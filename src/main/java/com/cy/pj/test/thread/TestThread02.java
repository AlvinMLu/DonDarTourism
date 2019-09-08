package com.cy.pj.test.thread;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestThread02 {
	static BlockingQueue<Runnable> workQueue=
			new ArrayBlockingQueue<>(1);
	
	static ThreadFactory factory=new ThreadFactory() {
		static final String prefix="CGB1903";
		private AtomicInteger aa=new AtomicInteger(0);
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r,prefix+"-"+aa.getAndIncrement());
		}
	};
	static ThreadPoolExecutor tPool=new ThreadPoolExecutor(
			3,//corePoolSize 核心线程数
			5,//maximumPoolSize最大线程数
			30, //keepAliveTime 空闲时间
			TimeUnit.SECONDS, //unit 时间单位
			workQueue,
			factory);//工作队列
	
	static void doTask(int n) {
		tPool.execute(new Runnable() {
			@Override
			public void run() {
				String name=Thread.currentThread().getName();
				System.out.println(name+" execute task "+n);
				try{Thread.sleep(3000);}catch(Exception e) {}
			}
		});
	}
	public static void main(String[] args) {
		doTask(1);
		doTask(2);
		doTask(3);
		doTask(4);
		doTask(5);
		doTask(6);

		
	}
}
