/**
 * The Truck Producer class creates the truck and updates them in the queue
 * 
 * @author Lakshmi Ravi
 * 
 */
public class Road extends Thread {
	MyCustomQueue<Truck> truckqueue;
	BridgeTrafficPost trafficpost;

	Road() {
		truckqueue = new MyCustomQueue<Truck>();
		trafficpost = new BridgeTrafficPost(truckqueue);
	}

	public void run() {

		// Create trucks and add them to the queue.
		trafficpost.start();

		for (int i = 0; i < 100; i++) {
			boolean truckDirection = i % 2 == 0;
			Truck trucki = new Truck(i, truckDirection, trafficpost);
			synchronized (truckqueue) {
				truckqueue.add(trucki);
				truckqueue.notify();
			}
		}
		truckqueue.setProcessDone();

	}

	public static void main(String[] args) {
		Road myRoadWays = new Road();
		myRoadWays.start();
	}
}
