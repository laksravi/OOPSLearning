public class T_1 extends Thread	{
	static int x = 1;
	String info = "";

	public T_1 (String info) {
		this.info = info;
		x++;
	}

	public void run () {
		x++;
		String output = x + " " + info + ": " + x;
		System.out.println(output);
	}

	public static void main (String args []) {
		new T_1("a").start();
		
		new T_1("b").start();
	}
}

/*
Possible combinations =>

1) 3 a: 3
   5 b: 5
   
   1 - constructor() -> x=2
   1 - run() -> x=3 , output = 3 a : 3
   
   2 - constructor() -> x=4
   2 - run() -> x=5 , output = 5 b : 5   


2) 4 a: 4
   5 b: 5

   1 - constructor() -> x=2
   1 - paused
   2 - constructor() -> x=3
   2 - paused
   1 - run() -> x=4 , output = 4 a : 4
   2 - run() -> x=5 , output = 5 b : 5   
   

3) 5 b: 5
   5 a: 5
  
   1 - constructor() -> x=2
   1 - run() -> x=3
   1 - paused
   2 - constructor() -> x=4
   2 - run() -> x=5 ,  output = 5 b: 5   
   1 - run() -> x=5 , output = 5 a: 5
    
    
4) 5 a: 5
   5 b: 5
  
   1 - constructor() -> x=2
   1 - run() -> x=3
   1 - paused
   2 - constructor() -> x=4
   2 - run() -> x=5 
   2 - paused   
   1 - run() -> x=5 , output = 5 a : 5    
   2 - run() -> x=5 , output = 5 b : 5    
    

5) Possible when Thread 1 lags behind Thread 2 while calling constructor itself 

   3 b: 3
   5 a: 5
   
   2 - constructor() -> x=2
   2 - run() -> x=3 , output = 3 b : 3
   
   1 - constructor() -> x=4
   1 - run() -> x=5 , output = 5 a : 5 


6) 3 a: 4
   5 b: 5
   
   1 - constructor() -> x=2
   1 - run() , output = 3 a : --- Paused before concatenating 2nd "x" value
   2 - constructor -> x=4 
   1 - run() , output  = 3 a : 4
   2 - run() x=5 , output = 5 b : 5
   
   
7)  5 a: 5
	4 b: 5
	
   1 - constructor() -> paused before incrementing x
   2 - constructor() -> x=2
   1 - constructor() -> x=3
   2 - run() -> x=4 , output = 4 b :---- Paused before concatenating 2nd "x" value
   1 - run() -> x=5 , output = 5 a : 5
   2 - run() -> x=5 , output = 4 b : 5     
   
8) 5 b: 5	
   4 a: 5
   
   1 - constructor() -> x=2
   1 - run() -> ---- paused before incrementing x
   2 - constructor() -> x=3
   2 - run() -> ---- paused before incrementing x
   1 - run() -> x=4 , output = 4 a : ----- Paused before concatenating 2nd "x" value
   2 - run() -> x=5 , output = 5 b : 5  
   1 - run() -> output = 4 a : 5
   
   
9) 5 b: 5
   4 a: 4
   
   1 - constructor() -> x=2
   1 - run() -> ---- paused before incrementing x
   2 - constructor() -> x=3
   2 - run() -> ---- paused before incrementing x
   1 - run() -> x=4 , output = 4 a : 4 ----- Paused before printing output
   2 - run() -> x=5 , output = 5 b : 5  
   1 - run() -> print output = 4 a : 4   
   
    
*/
