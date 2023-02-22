package nl.vu.labs.phoenix.ap;

/* [1] Set Specification
 * 	   -- Complete the specification for a set interface.
 * 		  See the List interface for inspiration
 */

/** @elements
 *    Characters of type Char
 *  @structure
 *    linear
 *  @domain
 *    All letters and digits, starting with letter, at least one character
 *  @constructor
 *    There is a constructor that creates a new Identifier using the first read character.
 *  @precondition
 *    The given character is a letter
 *  @postcondition
 *    The new Identifier object is an Identifier containing the given character.
 **/
public interface IdentifierInterface {
	
	/* 
	 * [2] Mandatory methods. Make sure you do not modify these!
	 * 	   -- Complete the specifications of these methods
	 */
	
	/** @precondition
	 *    --
	 *  @postcondition
	 *    Returns a String with the value of the Identifier.
	 **/
	
	String value();
	
	/* 
	 * [3] Add anything else you think belongs to this interface 
	 */
	
	/** @precondition
	 *    The given character is letter.
	 *  @postcondition
	 *    The identifier has one character.
	 **/
	
	void init(char c);
	
	/** @precondition
	 *    The given character is alphanumeric.
	 *  @postcondition
	 *    The character is now added to the Identifier.
	 **/
	
	void add(char c);
	
	/** @precondition
	 *    --
	 *  @postcondition
	 *    The amount of elements of the identifier is returned.
	 **/
	
	int size();
	
	/** @precondition
	 *    
	 *  @postcondition
	 *    TRUE: The identifiers are equal.
	 *    FALSE: The identifiers are not equal.
	 **/
	
	boolean equals (Object d);
	
	/** @precondition
	 *    
	 *  @postcondition
	 *    The hashcode value of the identifier has been returned
	 **/
	
	int hashCode();
	
}
