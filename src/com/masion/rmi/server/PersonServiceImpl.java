package com.masion.rmi.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.masion.rmi.api.PersonEntity;
import com.masion.rmi.api.PersonService;

//此为远程对象的实现类，须继承UnicastRemoteObject
public class PersonServiceImpl extends UnicastRemoteObject implements PersonService {
    /**
     *
     */
    private static final long serialVersionUID = 6449924535205647059L;

    public PersonServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public List<PersonEntity> getList() throws RemoteException {
        System.out.println("Get Person Start!");
        List<PersonEntity> personList = new LinkedList<PersonEntity>();

        PersonEntity person1 = new PersonEntity();
        person1.setAge(25);
        person1.setId(0);
        person1.setName("Leslie");
        personList.add(person1);

        PersonEntity person2 = new PersonEntity();
        person2.setAge(25);
        person2.setId(1);
        person2.setName("Rose");
        personList.add(person2);

        return personList;
    }

    @Override
    public List<PersonEntity> find(String name) throws RemoteException {
        PersonEntity person = new PersonEntity();
        person.setId(3);
        person.setName(name);
        person.setAge(131);
        List list = new ArrayList<PersonEntity>();
        list.add(person);
        return list;
    }

}