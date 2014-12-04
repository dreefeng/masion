package com.masion.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileUtil {

	/**
	 * 输出指定目录下所有文件的最后一行
	 *
	 */
	public static void getFilePath(String path){
		//String path = file.getPath();
		File file = new File(path);
		String aline = "";
		System.out.println(file);
		if(file.isDirectory()){
			File files[] = file.listFiles();

			for(File f:files){
				try{
				FileReader fr = new FileReader(f);
				BufferedReader br = new BufferedReader(fr);

				String line = br.readLine();
				while(line !=null){
					aline = line;
					line = br.readLine();
				}

				System.out.println(aline);

				br.close();
				fr.close();

				}catch(Exception e){
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * 比较两个文件的内容
	 *
	 */
	public static void compAndFilter(String src, String dest) {

		try {
			File sFile = new File(src);
			FileReader fr = new FileReader(sFile);
			BufferedReader br = new BufferedReader(fr);

			//FileInputStream fis = new FileInputStream(dest);

			String str = br.readLine();
			int idx = 0;
			boolean same = false;
			while (str != null) {
				System.out.print(++idx+" ");

				File dFile = new File(dest);
				FileReader fd = new FileReader(dFile);
				BufferedReader bd = new BufferedReader(fd);
				String dstr = bd.readLine().trim();
				same = false;
				while (dstr != null) {
					if(str.contains(dstr)){
						same = true;
						break;
					}
					dstr = bd.readLine();
				}
				if(!same){
					//System.out.println(str);
					System.err.println(str);
				}else
				System.out.println(str);
				str = br.readLine();
			}

			br.close();
			fr.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 读取文件内容，返回Properties格式<br>
	 *
	 *
	 * @param path
	 *            目标文件所在目录
	 * @param fileName
	 *            目标文件名称
	 *
	 * @return Properties格式内容
	 *
	 * @throws Exception
	 *             如果读取文件失败，则抛出异常
	 */
	public static Properties readPropertiesFile(String path, String fileName)
			throws Exception {

		Properties prop = new Properties();

		InputStream is = null;
		try {
			is = new FileInputStream(path + File.separator + fileName);
			prop.load(is);

			is.close();
			is = null;
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
					is = null;
				} catch (Exception e) {
					throw e;
				}
			}
		}

		return prop;
	}

	public static Boolean createDirFile(String path, String file) throws IOException{
		File p = new File(path);
		if(p.isDirectory()){
			File f = new File(p + File.separator + file);
			if(f.mkdir())	{
				System.out.println("Create path:" + f.getCanonicalPath() + " successfully.");
				System.out.println("length:" + f.getCanonicalPath().length());
				return true;
			}else{
				System.out.println("Create path:" + f.getCanonicalPath() + " failed.");
			}
		}else{
			System.out.println(path + " is not a directory!");
		}
		return false;
	}

	public static int pathLengthTest(String root, String prefix){
		int level = 1;
		String parent = root;

		try {
			while(level < 100){
				String file = prefix + (level++);
				if(!createDirFile(parent, file)){break;}
				parent = parent + File.separator + file;
//				System.out.println("parent: " + parent);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("layer: " + level);

		return level;
	}

	public static int fileNameLengthTest(String path, String prefix, String surffix){

		int len = prefix.length();
		File p = new File(path);
		if(p.isDirectory()){
			File f = new File(p + File.separator + prefix);
			try {
				while(f.createNewFile()){
					len = prefix.length();
					f.delete();
					prefix += surffix;
					f = new File(p + File.separator + prefix);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return len;
	}

	/**
	 * This method will delete you directory permanently, be carefully to do it.
	 * @param root
	 * @return
	 */
	public static String removeDir(String root){
		System.out.println("start :"+ root);
		File f = new File(root);
		if(f.isDirectory()){
			File[] files = f.listFiles();
			for(int i =0; i< files.length; i++){
				if(files[i].isDirectory()){
					try {
						removeDir(files[i].getCanonicalPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}else{
					files[i].delete();
				}
			}
		}
		f.delete();
		System.out.println("removed:"+ root);
		return root;
	}

	public static void testFileAttr(){

		File f = new File("myfile.txt");

		f.deleteOnExit();

		try {
			f.createNewFile();

			f.setReadOnly();

			System.out.println("file:"+ f.getCanonicalPath());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * test max file number in one directory
	 * @param path
	 * @param prefix
	 * @return
	 */
	public static int fileCountTest(String path, String prefix){
		int index = 0;
		File p = new File(path);
		if(p.isDirectory()){
			File f = new File(p + File.separator + prefix + "." + (index++) + ".txt");
			try {
				while(f.createNewFile()){
					String tempfile = 	prefix + "." + (index++) + ".txt";
					f = new File(p + File.separator + tempfile);
					System.out.println("file:"+ f.getCanonicalPath());
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return index;
	}

	public static String getTmpeFolder(){
	    String tmpPath = "";
	    try {
	        tmpPath = System.getProperty("java.io.tmpdir");
        } catch (Exception e) {
            e.printStackTrace();
        }
	    return tmpPath;
	}


	public static void main(String[] args) {

//		String fileNameConstraint = "\\/:?*\"<>|" ;
//		System.out.println("file can not name:"+ fileNameConstraint);
//		removeDir("D:" + File.separator + "com.sugon.cloudstor.clouddisk.test.1");

		String path = getTmpeFolder();
		System.out.println("Temp Path:" + path);

//		int filelen = fileLengthTest(root, prefix);
//		System.out.println("max file length:"+ filelen);
//		try {
//			Properties p = readPropertiesFile("","conf.properties");
//			System.out.println(p.get("mysqlPasswd"));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
