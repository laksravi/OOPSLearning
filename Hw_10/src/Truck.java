import java.util.Random;

/**
 * The class which models the vehicle
 * Each vehicle moves in a particular direction
 * has a weight and has a speed
 * @author laksh
 *
 */
public class Truck extends Thread{
	String truckname;
	int weight;
	int timeTakenToCross;
	boolean isDirectionLeft;
	boolean hastoCrossBridge=true;
	static Random rand = new Random();
	BridgeTrafficPost currentTrafficPost;
	
	Truck(int index, boolean direction, BridgeTrafficPost tp){
		truckname= "Truck : "+index;
		//Generate random number in a range , add minimum base
		weight=rand.nextInt(10000-100)+100;
		isDirectionLeft = direction;
		timeTakenToCross=rand.nextInt(1000-100)+100;
		currentTrafficPost = tp;
	}
	
	public void checkOut(){
		synchronized (currentTrafficPost) {
		currentTrafficPost.bridgeWeight-=weight;
		currentTrafficPost.truckCount--;
		}
		//Notify the first vehicle about checkout
		synchronized (currentTrafficPost.firstTruck) {
			currentTrafficPost.firstTruck.notifyAll();
		}
	}
	
	public void run(){
		System.out.println(this.truckname+" is crossing!!"+" time taken to cross is (s): "+ timeTakenToCross);
		try {
			sleep(timeTakenToCross);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		hastoCrossBridge=false;
		checkOut();
	}
	

}
