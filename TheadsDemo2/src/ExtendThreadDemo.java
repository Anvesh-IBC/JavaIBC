
public class ExtendThreadDemo {
 public static void main(String[] args) {
	 MyThread t1 = new MyThread();
	 t1.setName("Worker-1");
	 t1.start();
	 
	 System.out.println("Main thread: " + Thread.currentThread().getName());
 }
}
