public class T_2 extends Thread    {
    int id = 1;
    static String  theValue  = "1";
    T_2(int id)       {
        this.id = id;
    }
    public void run () {
        if ( id == 1 )
                theValue = "3";
        else
                theValue = "2";
    }      
        
    public static void main (String args []) {
        new T_2(1).start();;
        new T_2(2).start();;
            
        System.out.println("theValue = " + theValue ); // print1
        System.out.println("theValue = " + theValue ); // print2
    }       
}  

/*

1) theValue = 1
   theValue = 3

	1 -> constructor()
	main -> print1   theValue= 1
	1 -> run() : theValue=3
	main -> print2   theValue=3
	2 -> constructor()
	2 -> run() 		theValue=2


2) theValue = 3
   theValue = 3

	1 -> constructor()
	1 -> run() : theValue=3
	main -> print1   theValue= 3
	main -> print2   theValue=3
	2 -> constructor()
	2 -> run() 		theValue=2


3) theValue = 3
   theValue = 2

	1 -> constructor()
	1 -> run() : theValue=3
	main -> print1   theValue= 3
	2 -> constructor()
	2 -> run() 		theValue=2
	main -> print2   theValue=2


4) theValue = 1
   theValue = 1

	1 -> constructor()
	main -> print1   theValue= 1
	main -> print2   theValue=1
	1 -> run() : theValue=3
	2 -> constructor()
	2 -> run() 		theValue=2

5) theValue = 2
   theValue = 2

	1 -> constructor()
	1 -> run() : theValue=3
	2 -> constructor()
	2 -> run() 		theValue=2
	main -> print1   theValue= 2
	main -> print2   theValue=2


6) theValue = 2
   theValue = 3

	1 -> constructor()
	2 -> constructor()
	2 -> run() 		theValue=2
	main -> print1   theValue=2
	1 -> run() : theValue=3
	main -> print2   theValue=3
	
	
	*/
