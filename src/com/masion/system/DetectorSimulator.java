package com.masion.system;

public class DetectorSimulator {

    public static int count = 0;

    public static void FixNumberThread() {
        while (count < 400) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }

            System.out.println(new java.util.Date() + "-" + count++);
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            String execStr = "ipmitool -H 127.0.0.1 -U admin -P admin sensor";
                            String[] args = new String[] { "sh", "-c", execStr };
                            Process pr = Runtime.getRuntime().exec(args);
                            Thread.sleep(300);
                            pr.waitFor();
                            Thread.sleep(300000);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            }).start();
        }
    }

    public static void main(String[] args) {

        while (count < 400) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            }

            System.out.println(new java.util.Date() + "-" + count++);
            new Thread(new Runnable() {
                String execStr = "ipmitool -H 127.0.0.1 -U admin -P admin sensor";
                public void run() {
                    while (true) {
                        try {
                            CommandResult res = CmdWrapper.run(execStr, 60000L);
                            System.out.println(res.getStdOut());
                            Thread.sleep(300000);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                }
            }).start();
        }
    }

}
