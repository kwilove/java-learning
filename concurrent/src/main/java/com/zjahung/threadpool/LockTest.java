package com.zjahung.threadpool;

import com.zjahung.utils.ThreadGenerator;

public class LockTest {
	private static long num = 0;
	private static long num1 = 0;

	public static void main(String[] args) {
		// for (int index = 0; index < 1000; index++) {
		try {
			ThreadGenerator.createAndStart(10000, new Commander());
			new Thread(new Worker()).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
	}

	static class Commander implements Runnable {
		@Override
		public void run() {
			increase();
		}
	}

	private static void increase() {
		synchronized (LockTest.class) {
			num += 2;
		}
		num1 += 2;
	}

	static class Worker implements Runnable {
		@Override
		public void run() {
			System.out.println(this.getClass().getSimpleName() + ", the num is: " + num);
			System.out.println(this.getClass().getSimpleName() + ", the num1 is: " + num1);
		}
	}
}
