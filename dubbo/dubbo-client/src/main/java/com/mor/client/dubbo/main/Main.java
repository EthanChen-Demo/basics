package com.mor.client.dubbo.main;

import com.mor.client.dubbo.action.ChatAction;

public class Main {
	public static void main(String[] args) {
		ChatAction act = new ChatAction();
		act.SayHello();
	}
}
