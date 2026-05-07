
public class RunnableDemo {
public static void main(String[] args) {
	MyRunnable task= new MyRunnable();
	
	Thread t1 = new Thread(task, "Worker-A");
	Thread t2 = new Thread(task, "Worker-B");
	
	t1.start();
	t2.start();
	
}
}
