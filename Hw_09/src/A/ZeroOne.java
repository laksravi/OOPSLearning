package A;
public class ZeroOne extends Thread	{
	private String info;
	static Object o = new Object();
	static boolean LoopRunning = false;
	static int counter=0;
	private final int MAX=99;
					     
	public ZeroOne (String info) {
		this.info    = info;
	}
	public void run () {
		while ( true )	{
			synchronized ( o ) {
				if((Integer.parseInt(info)==counter)) {
				o.notifyAll();
				System.out.print(info+" ");
				counter=Integer.parseInt(info)+1;
				if(Integer.parseInt(info)==(MAX-1)){
					counter=0;
				}
				}
				try {
					if ( ! LoopRunning )	{
						for(int index=1;index<MAX;index++){
						( new ZeroOne(Integer.toString(index)) ).start();
						}
						LoopRunning = true;
					}
					//sleep(2);
					o.wait();
				} catch ( Exception e ) { }
			}
			}
	}
	public static void main (String args []) {
		new ZeroOne("0").start();
	}
}