package Aarti;
class multiplierThread implements Runnable {
	int index;
	int max;
	SieveOfEratosthenes s;

	public multiplierThread(SieveOfEratosthenes s, int index, int max) {
		this.index = index;
		this.max = max;
		this.s = s;
	}

	public void run() {
		s.markNonPrime(index, max);
	}
}

public class SieveOfEratosthenes {

	final static int FIRSTpRIMEuSED = 2;
	static int MAX;
	final boolean[] numbers;
	static int maxNumThreads;
	static int count = 0;

	public SieveOfEratosthenes(int max) {
		numbers = new boolean[max];
		MAX = max;
	}

	public void determinePrimeNumbers() {
		numbers[1] = false;
		for (int index = 2; index < MAX; index++) {
			numbers[index] = true;
		}

		for (int index = 2; index < (maxNumThreads + 2); index++) { 
			if (numbers[index]) { 
				Thread t = new Thread(new multiplierThread(this, index, MAX));
				t.start();
				System.out.println(" Thread.activeCount()= "
						+ Thread.activeCount());

			} 
		}
		
		while (Thread.activeCount() > 1) {
			System.out.println("Inside Thread.activeCount()= "
					+ Thread.activeCount());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
				e.printStackTrace();
			}
		}
		display();

	}

	void markNonPrime(int index, int max) {
		System.out.println("I have index= " + index + " and MAX value= " + max);
		int counter = 2;
		while (index * counter < max) { 
			numbers[index * counter] = false; 
			counter++;
		}

	}

	public void testForPrimeNumber() {

		int[] test = { 2, 3, 4, 6, 7, 8, 10, 11, 12, 13, 17, MAX - 1, MAX };
		for (int index = 0; index < test.length; index++) {
			if (test[index] < MAX) {
				System.out.println(test[index] + " = " + numbers[test[index]]);
			}
		}
	}

	public void display() {
		System.out.println("Found below are Prime numbers in range(0,"+MAX+") =>");
		for (int index = 1; index < MAX; index++) {
			if (numbers[index]) {
				System.out.println(index+" "+numbers[index]);
			}
		}
		System.out.print("\n\n");
	}

	public static void main(String[] args) {
		int numberOfThreads = 0;
		int range=0;
		if ((args.length < 0) && (args[0] == null)) {
			System.out.println("Please provide valid command line input,Try again");
			System.exit(0);
		}
		numberOfThreads = Integer.parseInt(args[0]);
		range=Integer.parseInt(args[1]);
		SieveOfEratosthenes.maxNumThreads = numberOfThreads;
		SieveOfEratosthenes aSieveOfEratosthenes = new SieveOfEratosthenes(range);
		aSieveOfEratosthenes.determinePrimeNumbers();
		aSieveOfEratosthenes.testForPrimeNumber();
		System.exit(0);
	}
}