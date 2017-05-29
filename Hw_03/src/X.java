class X {
	public static void main(String args[]) {

		int n = 0;

		// label
		here:
		// 1st While loop: always execute as set to True
		while (true) {

			System.out.println("Starting 1st for loop now");
			/*
			 * 2nd While loop : Checks whether value of n satisfies conditions
			 * mentioned below. While loop is executed only if both or either of
			 * first 2 conditions is true and 3rd condition is always true. AND
			 * operation performed. e.g. While loop will not be executed at all
			 * when n>=4
			 */

			while (((n != 3) || (n != 5)) && (n < 4)) {

				/*
				 * Below, we have nested if else loops. Conditions will be
				 * checked sequentially. If any of the condition is TRUE, block
				 * of code for that particular condition will be executed and
				 * all next conditions will not be checked at all.
				 */

				// value of n will be incremented first and then checked for
				// equality.
				/*
				 * (A) - Iteration 1 Before if n = 0 Increments n, now n = 1
				 * Checks for equity : not true -> goes to else condition (B)
				 */
				if (++n == 0)
					System.out.println("a/  n is " + n);

				// value of n will checked for equality first and then
				// incremented
				/*
				 * (B) Before else if n = 1 Checks if n == 1 : true, increments
				 * n : n =2 Prints n Goes to the next Iteration
				 */
				else if (n++ == 1) {
					System.out.println("b/  n is " + n);
				}

				// value of n will checked for equality first and then
				// incremented
				/*
				 * Condition : not checked during the first iteration of the
				 * while loop. The condition Will not be true during any
				 * iteration. n value incremented during the previous A and B
				 * blocks
				 */
				else if (n++ == 2) {
					System.out.println("This will not be Printed");
					System.out.println("c/  n is " + n);
				}

				// if none of above condition is satisfied , below else code is
				// executed
				else
					System.out.println("d/  n is " + n);

				// Just a print statement, no actual "break" happens here
				System.out.println("    executing break here");

			}

			/*
			 * Use of ternary operator : (a < b) ? a : b; If condition is true -
			 * a , false - b Here we have nested ternary operators. Depending on
			 * value of n, conditions will be checked and relevant part of code
			 * will be executed. n will have updated value here
			 */
			System.out.println(n % 2 == 0 ? (n == 4 ? "=" : "!")
					: (n % 3 != 0 ? "3" : "!3"));

			/*
			 * At this point, we are using labeled break operation. This command
			 * will break outermost while loop which is labeled and flow of
			 * control will go to code outside and after 1st while loop 
			 * [ while (true) loop ].
			 */

			break here;

		}
	}
}

/*
 * Output:
 * 
 * b/ n is 2 executing break here d/ n is 5 executing break here 3
 */