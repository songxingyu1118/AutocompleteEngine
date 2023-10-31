package cs1501_p2;

import java.util.ArrayList;
import java.util.Collections;

public class UserHistory implements Dict
{
    private ArrayList<String> words;
    private ArrayList<Integer> values;
    private String searchField;

    public UserHistory()
    {
        this.words = new ArrayList<String>();
        this.values = new ArrayList<Integer>();
        this.searchField = "";
    }

    public void add(String key)
    {
        if(!words.contains(key))
        {
            words.add(key);
            values.add(1);
        }
        int index = words.indexOf(key);
        values.set(index, (values.get(index) + 1));
    }

    public boolean contains(String key)
    {
        return words.contains(key);
    }

    public boolean containsPrefix(String key)
    {
        int length = key.length();
        for(int i = 0; i < words.size(); i++) if((words.get(i).length() > length) && (words.get(i).substring(0, length - 1).equals(key))) return true;
        return false;
    }

    public int searchByChar(char next)
    {
        searchField += next;
        boolean contains = contains(searchField);
        boolean containsPrefix = containsPrefix(searchField);
        if(containsPrefix)
        {
            if(contains) return 2;
            else return 0;
        }
        else
        {
            if(contains) return 1;
            else return -1;
        }
    }

    public void resetByChar() {searchField = "";}

    public ArrayList<String> traverse()
    {
        return words;
    }

    public int count()
    {
        return words.size();
    }

    public ArrayList<String> suggest()
    {
        ArrayList<String> suggest = new ArrayList<String>();
        ArrayList<String> tempWords = new ArrayList<String>();
        ArrayList<Integer> tempCounts = new ArrayList<Integer>();
        int length = searchField.length();
        for(int i = 0; i < words.size(); i++)
        {
            String word = words.get(i);
            if(word.length() >= length)
            {
                if(word.substring(0, length - 1).equals(word))
                {
                    tempWords.add(word);
                    tempCounts.add(values.get(words.indexOf(word)));
                }
            }
        }

        for(int i = 0; (i < 5) && (i < tempWords.size()); i++)
        {
            int max = Collections.max(tempCounts);
            suggest.add(tempWords.get(tempCounts.indexOf(max)));
            tempCounts.set(tempCounts.indexOf(max), 0);
        }
        return suggest;
    }
}