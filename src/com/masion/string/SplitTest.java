/**
 *
 */
package com.masion.string;

/**
 * @author zhangjinfeng
 *
 */
public class SplitTest {

	/**
	 *
	 */
	public SplitTest() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	    String[] params = {"power=1345", "level=100", "hehehe"};
	    String suffix = "0x3a 0xd 0x1 $level %level% %level% %level% %level% %level%";
	    String suffix2 = "0x2e 0xd0 0x57 0x01 0x00 0x00 %power% 0x00";
        for (String cmd : params) {
            if (cmd.contains("=")) {
                String key = "%"+cmd.split("=")[0]+"%";
                String value = cmd.split("=")[1];
                System.out.println(key +"="+ value);
                suffix= suffix.replaceAll(key, value);
                suffix2 = suffix2.replaceAll(key, value);
            } else {
                suffix += " " + cmd;
            }
        }
        System.out.println(suffix);
        System.out.println(suffix2);

	}

}
