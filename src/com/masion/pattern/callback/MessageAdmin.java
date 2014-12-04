package com.masion.pattern.callback;

import java.util.Map;

public interface MessageAdmin {

	public String sendMsg(String to, String msg, MessageHandle result, Map opt);

}
