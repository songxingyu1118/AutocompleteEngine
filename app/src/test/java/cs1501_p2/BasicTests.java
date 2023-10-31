/**
 * Basic tests for CS1501 Project 2
 * @author	Dr. Farnan
 */
package cs1501_p2;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

import static java.time.Duration.ofSeconds;

class BasicTests {
	final int DEFAULT_TIMEOUT = 10;

	@Test
	@DisplayName("Testing DLB")
	void dlb_test() {
		assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
			String dict_fname = "build/resources/test/dictionary.txt";

			DLB dlb = new DLB();
			assertEquals(0, dlb.count(), "Should be empty");

			try (Scanner s = new Scanner(new File(dict_fname))) {
				while (s.hasNext()) {
					dlb.add(s.nextLine());
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			assertEquals(7, dlb.count(), "Incorrect number of keys");

			String[] checks = new String[] {"dict", "definite", "A"};
			for (String c : checks) {
				assertTrue(dlb.contains(c), "DLB should contain " + c);
			}

			checks = new String[] {"not", "there"};
			for (String c : checks) {
				assertTrue(!dlb.contains(c), "DLB should not contain " + c);
			}

			checks = new String[] {"i", "dict"};
			for (String c : checks) {
				assertTrue(dlb.containsPrefix(c), c + " should be a valid prefix");
			}

			assertEquals(-1, dlb.searchByChar('q'), "q should not be a prefix or key");
			dlb.resetByChar();
			assertEquals(0, dlb.searchByChar('d'), "d should be a valid prefix");
			assertEquals(0, dlb.searchByChar('i'), "di should be a valid prefix");
			assertEquals(0, dlb.searchByChar('c'), "dic should be a valid prefix");
			assertEquals(2, dlb.searchByChar('t'), "dict should be a valid prefix and key");
			dlb.resetByChar();
			assertEquals(0, dlb.searchByChar('i'), "i should be a valid prefix");
			assertTrue(dlb.contains("this"), "Should be able to still run contains");
			assertEquals(1, dlb.searchByChar('s'), "is should be a valid key, even if interrupted by contains");
			
			dlb.resetByChar();
			dlb.searchByChar('d');
			ArrayList<String> sugs = dlb.suggest();
			String[] expected = new String[] {"definite", "dict", "dictionary"};
			for (int i = 0; i < expected.length; i++) {
				assertEquals(expected[i], sugs.get(i), "Expected suggestion " + expected[i] + " got " + sugs.get(i));
			}

			ArrayList<String> trav = dlb.traverse();
			expected = new String[] {"A", "a", "definite", "dict", "dictionary", "is", "this"};
			for (int i = 0; i < expected.length; i++) {
				assertEquals(expected[i], trav.get(i), "Expected traversal item " + expected[i] + " got " + trav.get(i));
			}
		});
	}

	@Test
	@DisplayName("Testing UserHistory")
	void uh_test() {
		assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
			UserHistory uh = new UserHistory();
			assertEquals(0, uh.count(), "Should be empty");

			uh.add("user");
			uh.add("user");
			uh.add("user");
			uh.add("userland");
			uh.add("userland");
			uh.add("up");
			uh.add("up");
			uh.add("up");
			uh.add("up");
			uh.add("up");
			uh.add("unity");
			uh.add("unity");
			uh.add("usermode");
			uh.add("usermode");
			uh.add("ui");
			uh.add("ux");

			assertEquals(7, uh.count(), "Should have 7 distinct words");

			uh.searchByChar('u');
			ArrayList<String> sugs = uh.suggest();
			assertEquals("up", sugs.get(0), "First suggestion should be up");
			assertEquals("user", sugs.get(1), "Second suggestion should be user");
			String[] others = new String[] {"unity", "userland", "usermode"};
			for (String o : others) {
				assertTrue(sugs.contains(o), "Should suggest " + o);
			}
		});
	}

	@Test
	@DisplayName("Testing AutoCompleter")
	void ac_test() {
		assertTimeoutPreemptively(ofSeconds(DEFAULT_TIMEOUT), () -> {
			String dict_fname = "build/resources/test/dictionary.txt";
			String uhist_state_fname = "build/resources/test/uhist_state.p2";

			AutoCompleter ac = new AutoCompleter(dict_fname);

			ArrayList<String> sugs = ac.nextChar('d');
			String[] expected = new String[] {"definite", "dict", "dictionary"};
			for (int i = 0; i < expected.length; i++) {
				assertEquals(expected[i], sugs.get(i), "(Initial) Expected suggestion " + expected[i] + " got " + sugs.get(i));
			}

			ac.finishWord("dictionary");
			sugs = ac.nextChar('d');
			expected = new String[] {"dictionary", "definite", "dict"};
			for (int i = 0; i < expected.length; i++) {
				assertEquals(expected[i], sugs.get(i), "(finish dictionary) Expected suggestion " + expected[i] + " got " + sugs.get(i));
			}

			ac.finishWord("dip");
			ac.finishWord("dip");
			sugs = ac.nextChar('d');
			expected = new String[] {"dip", "dictionary", "definite", "dict"};
			for (int i = 0; i < expected.length; i++) {
				assertEquals(expected[i], sugs.get(i), "(finish dip x2) Expected suggestion " + expected[i] + " got " + sugs.get(i));
			}

			ac.saveUserHistory(uhist_state_fname);

			ac = new AutoCompleter(dict_fname, uhist_state_fname);
			sugs = ac.nextChar('d');
			expected = new String[] {"dip", "dictionary", "definite", "dict"};
			for (int i = 0; i < expected.length; i++) {
				assertEquals(expected[i], sugs.get(i), "(reloaded state) Expected suggestion " + expected[i] + " got " + sugs.get(i));
			}
		});
	}
}
