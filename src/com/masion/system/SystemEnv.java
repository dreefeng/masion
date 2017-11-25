package com.masion.system;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

/**
 * 打印当前进程的系统参数
 * 
 * @date 2017年11月10日 下午2:32:56
 * @author zhangjf
 */
public class SystemEnv {

    /**
     * 系统环境类
     * 
     * @param args
     */
    public static void main(String[] args) {
        Properties props = System.getProperties();
        Entry<Object,Object> prop = null;
        Iterator<Entry<Object,Object>> it=props.entrySet().iterator();
        for(prop = it.next(); it.hasNext(); prop = it.next()){
            System.out.println(prop);
        }
        
        System.out.println(props.get("os.arch"));
        System.out.println(props.get("os.name"));
        System.out.println(props.get("os.version"));

    }

}
