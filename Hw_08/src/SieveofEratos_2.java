class Multiplier extends Thread {
	int multiplier;
	int maxValue;
	SieveofEratos_2 gameThread;

	public Multiplier(int multiplier, int maxValue, SieveofEratos_2 gamethread) {
		this.multiplier = multiplier;
		this.maxValue = maxValue;
		this.gameThread = gamethread;
		synchronized (gamethread) {
			gameThread.invokedThreadCount = new Integer(
					gameThread.invokedThreadCount.intValue() + 1);
		}

	}

	public synchronized void run() {

		if (gameThread.currentThreadCount.intValue() >= 4) {
			try {
				System.err.println("waiting" + multiplier
						+ " current Thread count"
						+ gameThread.currentThreadCount);
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		synchronized (gameThread.currentThreadCount) {
			gameThread.currentThreadCount = Integer
					.valueOf(gameThread.currentThreadCount.intValue() + 1);
		}

		int index = 2;
		while (multiplier * index < maxValue) {
			gameThread.numbers[multiplier * index] = false;
			index++;
		}
		synchronized (gameThread.currentThreadCount) {
			gameThread.currentThreadCount = Integer
					.valueOf(gameThread.currentThreadCount.intValue() - 1);
		}
		synchronized (gameThread.invokedThreadCount) {
			gameThread.invokedThreadCount = Integer
					.valueOf(gameThread.invokedThreadCount.intValue() - 1);
		}
		notifyAll();
	}

}

public class SieveofEratos_2 {

	final static int FIRSTpRIMEuSED = 2;
	static int MAX;
	final boolean[] numbers;
	Integer thereadsInvokedAndActive[];
	Integer currentThreadCount;
	Integer invokedThreadCount;
	String behaviour;

	public SieveofEratos_2(int max) {
		numbers = new boolean[max];
		currentThreadCount = new Integer(0);
		invokedThreadCount = new Integer(0);
		this.MAX = max;
		behaviour = "";
	}

	public void determinePrimeNumbers() {
		for (int index = 1; index < MAX; index++) {
			numbers[index] = true;
		}

		int limit = (MAX > 10 ? (int) Math.sqrt(MAX) + 1 : 3);
		numbers[1] = false;
		for (int index = 2; index < limit; index++) {
			if (numbers[index] == true) {
				Multiplier thread = new Multiplier(index, MAX, this);
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

	public static void main(String[] args) {

		SieveofEratos_2 aSieveOfEratosthenes = new SieveofEratos_2(100);
		aSieveOfEratosthenes.determinePrimeNumbers();
		aSieveOfEratosthenes.testForPrimeNumber();
		System.exit(0);
	}
}
