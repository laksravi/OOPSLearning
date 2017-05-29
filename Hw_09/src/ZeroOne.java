public class ZeroOne extends Thread	{
	private int info;
	private static final int MAX_THREADS=99;
	static Object o = new Object();
	static int whatIsRunning = -1;
					     
	public ZeroOne (int info) {
		this.info    = info;
	}
	public void run () {
		
			synchronized ( o ) {
				o.notify();
				while((whatIsRunning+1)%MAX_THREADS != info){
					try {
						o.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.print(info+" ");
				//Delay quotient in the thread
				/*try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}*/
				whatIsRunning=info;
				//Create a new thread
				new ZeroOne((info+1)%MAX_THREADS).start();
			}
	}
	public static void main (String args []) {
		new ZeroOne(0).start();
	}
}