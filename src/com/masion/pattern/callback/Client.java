package com.masion.pattern.callback;

import java.util.Map;

public class Client implements MessageHandle {

	@Override
	public String execute() {
		System.out.println(new Exception("").getStackTrace()[0]);
		return null;
	}

	@Override
	public String execute(Object o) {
		System.out.println("execute:");
		System.out.println(o);
		return null;
	}

	public String sendMsg(String to, String msg, Map opt) {

		MessageAdmin a =  new Server();
		a.sendMsg(to, msg, this, opt);

		return null;
	}

	public String callback(){
		System.out.println(new Exception("").getStackTrace()[0]);
		return null;
	}

	public static void main(String[] args) {

		for (int i = 0; i < 10; i++) {
			Client client = new Client();
			System.out.println("client=" + client);
			client.sendMsg(i + "@sugon.com", "hello world", null);
		}

	}



}
