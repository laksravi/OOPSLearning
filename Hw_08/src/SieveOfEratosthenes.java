/**
 * Class which creates a thread to determine a multiple of a given index
 * @author Lakshmi Ravi
 *
 */
class MultiplierThread extends Thread {
	int multiplier;
	int maxValue;
	int maxThreadCount;
	SieveOfEratosthenes gameThread;

	public MultiplierThread(int multiplier, int maxValue,
			SieveOfEratosthenes gamethread, int maxthreads) {
		this.multiplier = multiplier;
		this.maxValue = maxValue;
		this.gameThread = gamethread;
		maxThreadCount=maxthreads;
		synchronized (gameThread) {
			gameThread.invokedThreadCount++;
		}

	}

	
	public synchronized void run() {
		
		if (gameThread.currentThreadCount >= maxThreadCount) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized (gameThread) {
			gameThread.currentThreadCount++;
		}

		//asynchronous part of determining it's multiples
		int index = 2;
		while (multiplier * index < maxValue) {
			gameThread.numbers[multiplier * index] = false;
			index++;
		}
		
		//decrement the invoked and current threadCounter
		synchronized (gameThread) {
			gameThread.currentThreadCount--;
			gameThread.invokedThreadCount--;
			notifyAll();
		}
		
	}

}

public class SieveOfEratosthenes {

	final static int FIRSTpRIMEuSED = 2;
	static int MAX;
	final boolean[] numbers;
	Integer thereadsInvokedAndActive[];
	Integer currentThreadCount;
	Integer invokedThreadCount;
	String behaviour;

	public SieveOfEratosthenes(int max) {
		numbers = new boolean[max];
		currentThreadCount = 0;
		invokedThreadCount = (0);
		this.MAX = max;
	}

	public void determinePrimeNumbers(int maxthreadCount) {
		for (int index = 1; index < MAX; index++) {
			numbers[index] = true;
		}

		int limit = (MAX > 10 ? (int) Math.sqrt(MAX) + 1 : 3);
		numbers[1]=false;
		for (int index = 2; index < limit; index++) {
			if (numbers[index] == true) {
				MultiplierThread thread = new MultiplierThread(index, MAX, this, maxthreadCount);
				thread.start();
			}
		}
		while (invokedThreadCount.intValue() != 0) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void testForPrimeNumber() {
		int[] test = { 2, 3, 4, 7, 13, 17, MAX - 1, MAX };

		for (int index = 0; index < test.length; index++) {
			if (test[index] < MAX) {
				System.out.println(test[index] + " = " + numbers[test[index]]);
			}
		}
		
	}
	
	public void printAllPrimes(){
		System.out.println("*************\nAll prime numbers are:");
		for (int index = 0; index < numbers.length; index++) {
			if (numbers[index]) {
				System.out.println(index);
			}
		}
	}

	public static void main(String[] args) {

		SieveOfEratosthenes aSieveOfEratosthenes = new SieveOfEratosthenes(
				20);
		
		if(args.length < 1){
			System.out.println("Use as : java SieveOfEratosthenes <Thread Count>");
			System.exit(0);
		}
		int threadCount = Integer.parseInt(args[0]);
		aSieveOfEratosthenes.determinePrimeNumbers(threadCount);
		aSieveOfEratosthenes.testForPrimeNumber();
		//aSieveOfEratosthenes.printAllPrimes();
		
		System.exit(0);
	}
}
