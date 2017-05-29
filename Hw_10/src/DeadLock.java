public class DeadLock extends Thread     {
		private String info;
        static Object o1=null;
        static Object o2=null;
      
        public DeadLock(String info,Object o1, Object o2){
        	this.info=info; this.o1=o1; this.o2=o2;
        }
        
        public static void method1(){
        	synchronized(o1){
        		try {
        			System.out.println(Thread.currentThread().getName()+" got access for o1 but now waiting for o2");
        			synchronized(o2){
                		o1.notify();
                		o2.wait();
            		}
        		} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName()+"I am interrupted");
				}
        	}
        	System.out.println(Thread.currentThread().getName()+" is done");
        }
        
        public static void method2(){
        	synchronized(o2){
        		try {
        			System.out.println(Thread.currentThread().getName()+" got access for o2 but now waiting for o1");
        			synchronized(o1){
        				System.out.println("Got both!"+Thread.currentThread());
                		o2.notify();
                		o1.wait();
            		}
        		} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName()+"I am interrupted");
				}
        	}
        	System.out.println(Thread.currentThread().getName()+" is done");
        }
        
		public void run () {
			
        	if(info=="0"){
        		method1();
        	}
        	else{
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		method2();
        	}
        }
		
		
        public static void main (String args []) {
	        	o1 = new Object();
	        	o2 = new Object();
	        	new DeadLock("1",o2,o1).start();
	        	new DeadLock("0",o1,o2).start();
	        	
        }
}
