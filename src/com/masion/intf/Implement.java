package com.masion.intf;


public class Implement implements InterfaceI {

	public Implement() {
		System.out.println("This is in impl constructor");
	}

	@Override
	public void method() {
		System.out.println("This is in impl method");
	}

}
