
public class Consumer extends Thread{
	MyCustomQueue<String> candyQueue;
	MyCustomQueue<String> wrapperQueue;
	MyCustomQueue<Box> boxQueue;
	Consumer(MyCustomQueue<String> candyQueue, MyCustomQueue<String> wrapperQueue, MyCustomQueue<Box> boxqueue){
		this.candyQueue=candyQueue; this.wrapperQueue=wrapperQueue; this.boxQueue= boxqueue;
	}
	//30 boxes can be stored
	int boxStorage=40;
	int boxsCreated=0;
	
	public void run(){
		while(boxsCreated < boxStorage){
		//Create wrapped candy array of storage capacity of the box
		//Add wrapped candies to the box
		//Print the box
		//Create 'n' such boxes
		String[] wrappedCandies= new String[Box.storageCapacity];
		for(int i=0;i<Box.storageCapacity;i++){
			String wrapper, candy;
			
			//Get the candy from candy queue
			synchronized (candyQueue) {
				while(candyQueue.getCount() == 0){
					System.out.println("waiting for candies!");
					try {
						candyQueue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				candy = candyQueue.remove();
				candyQueue.notifyAll();
			}
			
			//Get Wrapper from wrapper queue
			synchronized (wrapperQueue) {
				while(wrapperQueue.getCount() == 0){
					System.out.println("Waiting for wrappers");
					try {
						wrapperQueue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				wrapper=wrapperQueue.remove();	
				wrapperQueue.notifyAll();
			}
			
			wrappedCandies[i]=wrapper+"_ wrapped candy _ "+ candy;
		}
		Box currentbox;
		synchronized (boxQueue) {
			while(boxQueue.getCount()==0){
				System.out.println("Waiting for boxes");
				try {
					boxQueue.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			currentbox=boxQueue.remove();
			boxQueue.notifyAll();
		}
		
		/*
		 *Display the box and the candies stored in it 
		 */
		System.out.println(currentbox.getName());
		for(int i=0;i<Box.storageCapacity;i++){
			currentbox.wrappedCandy[i]=wrappedCandies[i];
			System.out.println("\t"+wrappedCandies[i]);
		}
		System.out.println("\n");
		boxsCreated++;
		}
		synchronized (wrapperQueue) {
			wrapperQueue.setProcessDone();
			wrapperQueue.notifyAll();
		}
		synchronized (candyQueue) {
			candyQueue.setProcessDone();
			candyQueue.notifyAll();
		}
		synchronized (boxQueue) {
			boxQueue.setProcessDone();
			boxQueue.notifyAll();
		}
		
		System.out.println("Candy packing done!! :)");
	}
	
	public static void main(String[] args){
		MyCustomQueue<String> candyqueue = new MyCustomQueue<String>();
		MyCustomQueue<String> wrapperqueue = new MyCustomQueue<String>();
		MyCustomQueue<Box> boxqueue = new MyCustomQueue<Box>();
		
		
		candyProducer candymachine = new candyProducer(candyqueue);
		candymachine.start();
		candyWrappingPaperProducer papermachine = new candyWrappingPaperProducer(wrapperqueue);
		papermachine.start();
		
		BoxProducer boxMaker = new BoxProducer(boxqueue);
		boxMaker.start();
		Consumer packingAgent = new Consumer(candyqueue,wrapperqueue,boxqueue);
		packingAgent.start();
	}
}
