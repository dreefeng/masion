package com.masion.system;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * 执行外部程序接口
 *
 * @author
 *
 */
public class CmdWrapper {
	/**
	 * 执行外部程序接口
	 *
	 * @param execStr
	 * @return String
	 */
	void CmdWappwer() {

	}

	public static String execute(String cmd){

        String[] args = new String[] { "sh", "-c", cmd };//linux
        if(isWindows()){
            args = new String[]{"cmd", "/C", cmd}; //windows
        }

        ProcessBuilder pb = new ProcessBuilder(args);
        pb.redirectErrorStream();
        String outInfo = "";
        int ptr = 0;
        Process proc = null;
        try {
            proc = pb.start();
            InputStream in = proc.getInputStream();
            InputStreamReader inr = new InputStreamReader(in);
            StringBuffer buffer = new StringBuffer();
            while ((ptr = inr.read()) != -1) {
                buffer.append((char) ptr);
            }
            outInfo = buffer.toString();

            proc.waitFor();

            inr.close();
            inr=null;
            in.close();
            in=null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (proc != null)
                try {
                    closeStreams(proc);
                    proc.destroy();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return outInfo;

	}

    public static CommandResult run(String command) {
        return run(command, null);
    }

    public static CommandResult run(String command, Long waitMilliSecond) {
        String[] cmds = { "/bin/sh", "-c", "LANG=C;" + command.trim() };

        if (isWindows()) {
            cmds = new String[] { "cmd", "/C", command.trim() };
        }
        return run(cmds, null, null, waitMilliSecond);
    }

    private static CommandResult run(String[] command, String[] envp, File dir, Long waitMilliSecond) {

		String stdOut = null;
		String stdErr = null;
		int exitVal = 0;

		CommandResult cr = null;
        if(waitMilliSecond == null || waitMilliSecond <= 0){
            waitMilliSecond = 600*1000L;
        }

		// 执行命令，并处理执行结果
		Process suProc = null;
		try {
			suProc = Runtime.getRuntime().exec(command, envp, dir);

			// any error message?
			StreamGobbler errorGobbler = new StreamGobbler(suProc, StreamGobbler.STREAM_TYPE_ERROR);

			// any output?
			StreamGobbler outputGobbler = new StreamGobbler(suProc, StreamGobbler.STREAM_TYPE_OUT);

			// kick them off
			outputGobbler.start();
			errorGobbler.start();

			// prevent return before start
			Thread.sleep(100);

			// wait for thread end
			int gobblerThreadCount = 0;
            long maxTime = waitMilliSecond / 100;
			while (!(errorGobbler.isQuit() && outputGobbler.isQuit()) && maxTime > gobblerThreadCount++) {
				Thread.sleep(100);
			}

			stdOut = outputGobbler.getStdOut();
			stdErr = errorGobbler.getStdError();

            if (maxTime <= gobblerThreadCount) {
                exitVal = -4;
                String timeOutMsg = "Wait for process over time [" + waitMilliSecond + "ms], so return interuptly.";
                if(null == stdErr){
                    stdErr = timeOutMsg;
                }else{
                    stdErr = timeOutMsg + "\nstderr:" + stdErr;
                }
            }else{
                exitVal = suProc.waitFor();
            }
			cr = new CommandResult(exitVal, stdOut, stdErr);

		} catch (IOException e) {
			e.printStackTrace();
			cr = new CommandResult(-2, "LocalMethod.run(IOException)", e.toString());
		} catch (InterruptedException e) {
			e.printStackTrace();
			cr = new CommandResult(-3, "LocalMethod.run(InterruptedException)", e.toString());
		} finally {
			if (suProc != null)
				try {
					closeStreams(suProc);
					suProc.destroy();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return cr;

	}

	public static CommandResult run(String[] commands) {
	    return run(commands, null, null, null);
	}

	public static void closeStreams(Process p) throws IOException {
		p.getInputStream().close();
		p.getOutputStream().close();
		p.getErrorStream().close();
	}

    public static Boolean isWindows() {
        String os = System.getProperty("os.name");
        if (os.startsWith("win") || os.startsWith("Win")) {
            return true;
        } else {
            return false;
        }
    }

	public static void main(String[] argv){
	    String cmd = "";
	    for(int i=0; i<argv.length;i++){
	        cmd += argv[i] + " ";
	    }
        System.out.println("cmd:" + cmd);
	    CommandResult result = CmdWrapper.run(argv);
        System.out.println("exit value:" + result.getExitVal());
        System.out.println("stdout:");
	    System.out.println(result.getStdOut());
        System.err.println("stderr:");
        System.err.println(result.getStdErr());
	}

}
