import java.util.Vector; //imports vector utility

class ConstantOrNot {

	private final int aInt = 1;
	private final String aString = "abc";
	private final Vector aVector = new Vector();

	public void doTheJob() {

		/*
		 * aInt is declared as Final. Final variable can be initialized only
		 * once which is already done with code: private final int aInt = 1;
		 * Here we are trying to change value of final aInt from 1 to 3 which is
		 * not possible. hence this would fail
		 */

		// aInt = 3; why would this fail?

		/*
		 * aString is declared as Final. Final variable can be initialized only
		 * once which is already done with code : private final String aString =
		 * "abc"; Here we are trying to concatenate new string "abc" to Final
		 * aString. hence,modification of Final string will result into failure
		 */

		// aString = aString + "abc"; why would this fail?

		/*
		 * aVector is declared as Final(private final Vector aVector = new
		 * Vector()) which means that later in program, aVector can not point to
		 * some other vector.
		 * 
		 * The value of the final variable holds the address X of the Vector which
		 * would remain constant throughout the program
		 * 
		 * The value pointed in the address is altered, which doesn't affect the
		 * definition of final variable
		 * 
		 * But we can modify vector to which "aVector" is pointing to by adding
		 * or removing items to it. hence below line of code will work
		 * successfully
		 */

		aVector.add("abc"); // why does this work?

	}

	public static void main(String args[]) {
		new ConstantOrNot().doTheJob();
	}

}