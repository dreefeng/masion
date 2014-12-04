/**
 * @Title: PathCompare.java
 * @project Gridview V3.0.0
 * @subproject masion V3.0.0
 *
 * Copyright 2014 sugon, Inc. All rights reserved.
 * SUGON PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.masion.io;

/**
 * @class PathCompare
 * @date 2014-8-26 上午11:33:02
 * @author zhang
 */
public class PathCompare {

    /**
     * 创建一个新的实例 PathCompare
     */
    public PathCompare() {
    }

    public static Boolean compare(String... paths) {

        System.out.println("start compare");
        for (String path1 : paths) {
            System.out.println(path1);
            for (String path2 : paths) {
                if (path1 == path2) {
                    System.out.println(path1.hashCode() + "  =  " + path2.hashCode());
                } else {
                    if ((path1.startsWith(path2) || path2.startsWith(path1))) {
                        System.out.println(path1 + "  conflict with  " + path2);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * @param args
     * @date 2014-8-26 上午11:33:03
     * @author zhang
     */
    public static void main(String[] args) {

        String path1 = "/usr/local/";
        String path2 = "/opt/cloudstor";
        String path3 = "/usr/local/cloudstor/";
        String path4 = "/usr/local/mysql/";
        String path5 = "/cloudstor/";
        String path6 = "/srv/www";

        System.out.println(PathCompare.compare(path1, path2, path3, path4, path5, path6));

    }

}
