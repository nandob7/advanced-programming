package nl.vu.labs.phoenix.ap;

/* [1] Set Specification
 * 	   -- Complete the specification for a set interface.
 * 		  See the List interface for inspiration
 */

/** @elements
 *    objects of type T
 *  @structure
 *    --
 *  @domain
 *    All possible objects T.
 *  @defaultconstructor
 *    There is a constructor that creates a new empty Set object.
 *  @precondition
 *    --
 *  @postcondition
 *    The new empty Set object is created.
 **/
public interface SetInterface<T extends Comparable<T>> {
	
	/* 
	 * [2] Mandatory methods. Make sure you do not modify these!
	 * 	   -- Complete the specifications of these methods
	 */
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  TRUE - copy of the element was inserted
	 * 	  FALSE - the element was already present 
	 **/
	boolean add(T t);
	
	/** @precondition
	 *    The set is not empty.
	 *  @postcondition
	 * 	  A copy of a random element in the set has been returned
	 **/
	
	T get();
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  TRUE  - The element has been removed from the set.
	 * 	  FALSE - The element was not in the set and has not been removed.
	 **/
	
	boolean remove(T t);
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  The amount of elements in the set has been returned. 
	 **/
	
	int size();
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  A copy of the set has been returned.
	 **/
	
	SetInterface<T> copy();
	
	/*
	 * [3] Methods for set operations 
	 * 	   -- Add methods to perform the 4 basic set operations 
	 * 		  (union, intersection, difference, symmetric difference)
	 */
	
	
	// your code here
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  A set containing all elements of both sets without duplicates has been returned.
	 **/
	
	SetInterface<T> union(SetInterface<T> t);
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  A set containing all elements which are in both sets is returned.
	 **/
	
	SetInterface<T> intersection(SetInterface<T> t);
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  A set containing all elements which are in the first set but not in the second set is returned.
	 **/
	
	SetInterface<T> complement(SetInterface<T> t);
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  A set containing all elements of both sets which are not in the intersection of both sets is returned.
	 **/
	
	SetInterface<T> symmetricDifference(SetInterface<T> t);
	
	/* 
	 * [4] Add anything else you think belongs to this interface 
	 */
	
	// your code here
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  The set is empty.
	 **/
	
	void init();
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  TRUE  - The given element is in the set.
	 * 	  FALSE - The given element is not in the set.
	 **/
	
	boolean isInSet(T t);
	
	/** @precondition
	 *    --
	 *  @postcondition
	 * 	  TRUE  - The set is empty.
	 *    FALSE - The set is not empty.  
	 **/

	boolean isEmpty();
}
