class MyThread extends Thread {
 @Override
 public void run() {
	 System.out.println("Child thread is running: " + Thread.currentThread().getName());
 }
}

