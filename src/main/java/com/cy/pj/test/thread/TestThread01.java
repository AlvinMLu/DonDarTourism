package com.cy.pj.test.thread;
public class TestThread01 {
	static String content;
	public static void main(String[] args) throws InterruptedException {
		Thread t=new Thread() {
			@Override
			public void run() {
				content="helloworld";
			}
		};
		t.start();
		t.join();
		System.out.println(content.toUpperCase());
	}
}
