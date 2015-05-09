package com.masion.rmi.client;

import java.rmi.Naming;
import java.util.List;

import com.masion.rmi.api.PersonEntity;
import com.masion.rmi.api.PersonService;

public class Client {
    public static void main(String[] args) {
        try {
            //调用远程对象，注意RMI路径与接口必须与服务器配置一致
            PersonService personService = (PersonService) Naming.lookup("rmi://127.0.0.1:6600/PersonService");
            List<PersonEntity> personList = personService.getList();
            for (PersonEntity person : personList) {
                System.out.println("ID:" + person.getId() + " Age:" + person.getAge() + " Name:" + person.getName());
            }

            List<PersonEntity> pl = personService.find("who");
            for (PersonEntity person : pl) {
                System.out.println("ID:" + person.getId() + " Age:" + person.getAge() + " Name:" + person.getName());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}