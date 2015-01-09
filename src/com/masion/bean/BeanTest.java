package com.masion.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {

    /**
     * @param args
     */
    public static void main(String[] args) {

        ApplicationContext ctx = new ClassPathXmlApplicationContext(
                "com/masion/bean/applicationContext.xml");
        Bean bean = (Bean) ctx.getBean("alarmBean");
        System.out.println(bean);

    }

}
