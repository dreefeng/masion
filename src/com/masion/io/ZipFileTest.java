package com.masion.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileTest {

    public ZipFileTest() {
    }

    /**
     * 压缩单个文件
     */
    public static void ZipFile(String filepath, String zippath) {
        try {
            File file = new File(filepath);
            File zipFile = new File(zippath);
            InputStream input = new FileInputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            int temp = 0;
            while ((temp = input.read()) != -1) {
                zipOut.write(temp);
            }
            input.close();
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一次性压缩多个文件，文件存放至一个文件夹中
     */
    public static void ZipMultiFile(String filepath, String zippath) {
        try {
            File file = new File(filepath);// 要被压缩的文件夹
            File zipFile = new File(zippath);
            InputStream input = null;
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; ++i) {
                    input = new FileInputStream(files[i]);
                    zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));
                    int temp = 0;
                    while ((temp = input.read()) != -1) {
                        zipOut.write(temp);
                    }
                    input.close();
                }
            }
            zipOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压缩（解压缩单个文件）
     */
    public static void UnZipFile(String zippath, String outfilepath, String filename) {
        try {
            File file = new File(zippath);//压缩文件路径和文件名
            File outFile = new File(outfilepath);//解压后路径和文件名
            ZipFile zipFile = new ZipFile(file);
            ZipEntry entry = zipFile.getEntry(filename);//所解压的文件名
            InputStream input = zipFile.getInputStream(entry);
            OutputStream output = new FileOutputStream(outFile);
            int temp = 0;
            while ((temp = input.read()) != -1) {
                output.write(temp);
            }
            input.close();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ListZipFile(String zippath, String filelistpath) {
        try {
            File file = new File(zippath);
            File outFile = null;
            ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
            ZipEntry entry = null;
            OutputStream output = null;
            if(filelistpath!=null){
                outFile = new File(filelistpath);
                output = new FileOutputStream(outFile);
            }
            int index = 0;
            while ((entry = zipInput.getNextEntry()) != null) {
                String fileEntry = ++index + "\t" + entry.getName()+"\n";
                System.out.print(fileEntry);
                if(output!=null){
                    output.write(fileEntry.getBytes());
                }
            }
            if(output!=null){
                output.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 解压缩（压缩文件中包含多个文件）可代替上面的方法使用。
     * ZipInputStream类 当我们需要解压缩多个文件的时候，ZipEntry就无法使用了，
     * 如果想操作更加复杂的压缩文件，我们就必须使用ZipInputStream类
     */
    public static void UnZipMultiFile(String zippath, String outzippath) {
        try {
            File file = new File(zippath);
            File outFile = null;
            ZipFile zipFile = new ZipFile(file);
            ZipInputStream zipInput = new ZipInputStream(new FileInputStream(file));
            ZipEntry entry = null;
            InputStream input = null;
            OutputStream output = null;
            while ((entry = zipInput.getNextEntry()) != null) {
                System.out.println("解压缩:" + entry.getName());
                outFile = new File(outzippath + File.separator + entry.getName());
                if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdir();
                }
                if (!outFile.exists()) {
                    outFile.createNewFile();
                }
                System.out.println("文件:" + outFile.getCanonicalPath());
                input = zipFile.getInputStream(entry);
                output = new FileOutputStream(outFile);
                int temp = 0;
                while ((temp = input.read()) != -1) {
                    output.write(temp);
                }
                input.close();
                output.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        ZipFileTest.UnZipMultiFile("E:\\GridviewBundle\\core\\pageintegration.application.pageframe-3.0.0-SNAPSHOT.jar", "D:\\tmp\\page");
    }

}
