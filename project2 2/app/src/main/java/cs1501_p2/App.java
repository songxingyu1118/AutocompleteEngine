/**
 * A driver for CS1501 Project 2
 * @author	Dr. Farnan
 */
package cs1501_p2;

import java.io.File;
import java.util.ArrayList;

public class App {
	public static void main(String[] args) {
		String eng_dict_fname = "build/resources/main/dictionary.txt";
		String uhist_state_fname = "build/resources/main/uhist_state.p2";

		AutoCompleter ac;
		File check = new File(uhist_state_fname);
		if (check.exists()) {
			ac = new AutoCompleter(eng_dict_fname, uhist_state_fname);
		}
		else {
			ac = new AutoCompleter(eng_dict_fname);
		}

		printPredictions(ac, 't');
		printPredictions(ac, 'h');
		printPredictions(ac, 'e');
		printPredictions(ac, 'r');
		printPredictions(ac, 'e');

		String word = "thereabout";
		System.out.printf("Selected: %s\n\n", word);
		ac.finishWord(word);

		printPredictions(ac, 't');
		printPredictions(ac, 'h');
		printPredictions(ac, 'e');
		printPredictions(ac, 'r');
		printPredictions(ac, 'e');		

		ac.saveUserHistory(uhist_state_fname);
	}

	private static void printPredictions(AutoCompleter ac, char next) {
		System.out.printf("Entered: %c\n", next);

		ArrayList<String> preds = ac.nextChar(next);

		System.out.println("Predictions:");
		int c = 0;
		for (String p : preds) {
			System.out.printf("\t%d: %s\n", ++c, p);
		}
		System.out.println();
	}
}
