/**
 * Autocomplete specification interface for CS1501 Project 2
 * @author	Dr. Farnan
 */
package cs1501_p2;

import java.util.ArrayList;

interface AutoComplete_Inter {

	/**
	 * Produce up to 5 suggestions based on the current word the user has
	 * entered These suggestions should be pulled first from the user history
	 * dictionary then from the initial dictionary. Any words pulled from user
	 * history should be ordered by frequency of use. Any words pulled from
	 * the initial dictionary should be in ascending order by their character
	 * value ("ASCIIbetical" order).
	 *
	 * @param 	next char the user just entered
	 *
	 * @return	ArrayList<String> List of up to 5 words prefixed by cur
	 */	
	public ArrayList<String> nextChar(char next);

	/**
	 * Process the user having selected the current word
	 *
	 * @param 	cur String representing the text the user has entered so far
	 */
	public void finishWord(String cur);

	/**
	 * Save the state of the user history to a file
	 *
	 * @param	fname String filename to write history state to
	 */
	public void saveUserHistory(String fname);
}
