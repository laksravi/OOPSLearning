public class candyProducer extends Thread {

	MyCustomQueue<String> candyQueue;
	int MAX_CANDIES = 100;
	int count = 0;

	public candyProducer(MyCustomQueue<String> candyArray) {
		this.candyQueue = candyArray;
	}

	public void run() {
		while (true) {
			String candyString = "Candy_number" + count++;
			// Check for number of candies and if it's greater than MAX add
			// candy to the queue
			if(candyQueue.isProcessDone){
				return;
			}
			synchronized (candyQueue) {
				
				while (candyQueue.getCount() == MAX_CANDIES) {
					try {
						candyQueue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(candyQueue.isProcessDone){
						return;
					}
				}
				candyQueue.add(candyString);
				candyQueue.notifyAll();
			}
		}
	}

}
