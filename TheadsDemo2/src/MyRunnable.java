
class MyRunnable implements Runnable {
@Override 
public void run() {
	System.out.println("Runnable task excuted by: "+ Thread.currentThread().getName() );
}

}
