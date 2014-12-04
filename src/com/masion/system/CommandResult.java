package com.masion.system;

/**
 * 保存本地命令调用方法的返回结果的类
 * @author MyMaster
 *
 */
public class CommandResult {

	//保存调用命令后的返回值
	private int exitVal;

	//保存命令调用的标准输出
	private String stdOut;

    //保存调用命令的错误输出
	private String stdErr;

    /**
     * 获取命令返回值
     * @return
     */
    public int getExitVal() {
		return exitVal;
	}

    /**
     * 设置命令返回值
     * @param exitVal
     */
	public void setExitVal(int exitVal) {
		this.exitVal = exitVal;
	}

	/**
	 * 获取命令标准输出
	 * @return
	 */
	public String getStdOut() {
		return stdOut;
	}

	/**
	 * 设置命令标注输出
	 * @param stdOut
	 */
	public void setStdOut(String stdOut) {
		this.stdOut = stdOut;
	}

	/**
	 * 获取命令的错误输出
	 * @return
	 */
	public String getStdErr() {
		return stdErr;
	}

	/**
	 * 设置命令的错误输出
	 * @param stdErr
	 */
	public void setStdErr(String stdErr) {
		this.stdErr = stdErr;
	}

	/**
	 * 无参数的构造函数
	 */
    public CommandResult()
    {
    }

    /**
     * 带参数的构造函数
     * @param exitVal
     * @param stdOut
     * @param stdErr
     */
    public CommandResult(int exitVal, String stdOut, String stdErr)
    {
        this.exitVal = exitVal;
        this.stdOut = stdOut;
        this.stdErr = stdErr;
    }

}
