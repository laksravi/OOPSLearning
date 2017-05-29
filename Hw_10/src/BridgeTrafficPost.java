/**
 * This class provides the functions of orignising the queue
 * The vehicle is allowed only if the bridge can tolerate it
 *  
 * @author Lakshmi Ravi
 * @author Aarti Gorde
 *
 */
public class BridgeTrafficPost extends Thread {
	static final int MAX_WEIGHT = 20000;
	static final int MAX_TRUCK = 4;
	int truckCount = 0;
	int bridgeWeight = 0;
	MyCustomQueue<Truck> trafficqueue;
	Truck firstTruck;

	public BridgeTrafficPost(MyCustomQueue<Truck> traffic) {
		this.trafficqueue = traffic;
		
	}

	public void run() {

		while (true) {
			// Wait till trucks arrive to the post
			synchronized (trafficqueue) {
				while (trafficqueue.nodeCount == 0) {
					if(trafficqueue.isProcessDone){
						return;
					}
					try {
						trafficqueue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//queueCount = trafficqueue.nodeCount;
				firstTruck = trafficqueue.remove();

			}
								

				// Wait if the truck count is more or the weight is more
				synchronized (firstTruck) {
					while (bridgeWeight + firstTruck.weight > MAX_WEIGHT
							|| (truckCount > MAX_TRUCK - 1)) {
						try {
							System.out.println("Waiting \n\t Weight : "
									+ bridgeWeight + " truck count"
									+ truckCount);
							firstTruck.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

				// Move the truckss
				synchronized (this) {
					this.truckCount++;
					this.bridgeWeight += firstTruck.weight;
				}
				firstTruck.start();
			}
		
	}

}
