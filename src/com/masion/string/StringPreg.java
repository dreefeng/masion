package com.masion.string;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringPreg {

	/**
	 * 输入校验
	 *
	 * @param text
	 * @param validater
	 * @return boolean
	 */
	public static boolean inputValidater(String text, String validater) {
		Pattern pattern = Pattern.compile(validater);
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}

//	public static final String VALIDATER_FILENAME="[|':?*\"\\/<>]";
	public static final String VALIDATER_FILENAME = "[|':?*><\"\\\\]";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		String text = "?";
//		System.out.println(VALIDATER_FILENAME + "\n " + text + " : " + StringPreg.inputValidater(text, VALIDATER_FILENAME));

	    for(String arg: args){
	     if ((!arg.startsWith("scan")) && (!arg.startsWith("/")) && (!arg.contains(":")) && (arg.split("/").length <= 3) && (!new File(arg).exists()))
	        {
	            System.out.println(arg + "  V");
	        }else{
	            System.out.println(arg + "  X");
	        }
	    }
	}




}
