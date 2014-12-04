package com.masion.string;

public class HashCode {
	private static final int HASHCODE_LENGTH = 11;//字符串的hashcode目标长度

	/**
	 * 字符串生成11位数字，str==null时返回null
	 * @param str 源字符串
	 * @return str的hashcode，补全为11位Long，如str的hashcode为567890，则返回11111567890
	 */
	public static Long getStrHashCode(String str){
		if (str == null){
			return null;
		}
		String hashCode = (str.hashCode() + "").replace('-', '1');
		System.out.println(".........hashCode"+hashCode);
		int len = HASHCODE_LENGTH - hashCode.length();
		String prefix = "";
		for (int i = 0; i < len; i++){
			prefix += "1";
		}
		return Long.parseLong(prefix + hashCode);
	}

	/**
	 * 字符串生成11位数字，str1==null或str2==null时返回null
	 * @param str1 源字符串1
	 * @param str2 源字符串2
	 * @return (str1+str2)的hashcode，补全为11位Long，如str1+str2的hashcode为567890，则返回11111567890
	 */
	public static Long getStrHashCode(String str1, String str2){
		if (str1 ==null || str2 == null){
			return null;
		}
		return getStrHashCode(str1 + str2);
	}

	/**
	 * 数字前缀拼接字符串hashcode11位转换值，如prefix为1234,str的hashcode为567890，则返回123411111567890
	 * @param prefix 数字前缀
	 * @param str 源字符串
	 * @return 目标数字，str生成的11位数字在前面拼接prefix
	 */
	public static Long getConcatHashCode(Long prefix, String str){
		Long hashCode = getStrHashCode(str);
		if (hashCode != null){
			return Long.parseLong(prefix + "" + hashCode);
		}
		else {
			return null;
		}
	}

	/**
	 * 数字前缀拼接字符串hashcode11位转换值，如prefix为1234,str1+str2的hashcode为567890，则返回123411111567890
	 * @param prefix 数字前缀
	 * @param str1 源字符串1
	 * @param str2 源字符串2
	 * @return 目标数字，str1+str2生成的11位数字在前面拼接prefix
	 */
	public static Long getConcatHashCode(Long prefix, String str1, String str2){
		Long hashCode = getStrHashCode(str1, str2);
		if (hashCode != null){
			return Long.parseLong(prefix + "" + hashCode);
		}else {
			return null;
		}
	}

	public static void main(String[] args) {
		Long merticid = HashCode.getConcatHashCode((long) 1001, "node20", "11912384914");
		System.out.println("" + merticid);
	}

}
