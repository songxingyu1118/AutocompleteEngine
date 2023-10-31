/**
 * DLBNode class for CS1501 Project 2
 * @author	Dr. Farnan
 */
package cs1501_p2;

import java.io.Serializable;

public class DLBNode implements Serializable {
	
	/**
	 * Letter represented by this DLBNode
	 */
	private char let;

	/**
	 * Lead to other alternatives for current letter in the path
	 */
	private DLBNode right;

	/**
	 * Leads to keys with prefixed by the current path
	 */	
	private DLBNode down;

	/**
	 * Constructor that accepts the letter for the new node to represent
	 */
	public DLBNode(char let) {
		this.let = let;
		
		this.right = null;
		this.down = null;
	}

	/**
	 * Getter for the letter this DLBNode represents
	 *
	 * @return	The letter
	 */
	public char getLet() {
		return let;
	}

	/**
	 * Getter for the next linked-list DLBNode
	 *
	 * @return	Reference to the right DLBNode
	 */
	public DLBNode getRight() {
		return right;
	}

	/**
	 * Getter for the child DLBNode
	 *
	 * @return	Reference to the down DLBNode
	 */
	public DLBNode getDown() {
		return down;
	}

	/**
	 * Setter for the next linked-list DLBNode
	 *
	 * @param	r DLBNode to set as the right reference
	 */
	public void setRight(DLBNode r) {
		right = r;
	}

	/**
	 * Setter for the child DLBNode
	 *
	 * @param	d DLBNode to set as the down reference
	 */
	public void setDown(DLBNode d) {
		down = d;
	}
}
