package cs1501_p2;

import java.util.ArrayList;

public class DLB implements Dict
{
    private DLBNode head;
    private String searchField;
    private final char TERMINATOR = '.';

    public DLB()
    {
        this.head = null;
        this.searchField = "";
    }

    public void add(String key)
    {
        if(key.length() < 1) return;
        if(head == null) head = new DLBNode(key.charAt(0));
        DLBNode curr = head;
        for(int i = 0; i < key.length(); i++)
        {
            char letter = key.charAt(i);
            while(curr != null)
            {
                if(curr.getLet() == letter) break;
                if(curr.getRight() == null) curr.setRight(new DLBNode(letter));
                curr = curr.getRight();
            }
            if(i == (key.length() - 1)) break;
            if(curr.getDown() == null) curr.setDown(new DLBNode(key.charAt(i + 1)));
            curr = curr.getDown();
        }
        if(curr.getDown() == null)
        {
            curr.setDown(new DLBNode(TERMINATOR));
            return;
        }
        curr = curr.getDown();
        while(curr != null)
        {
            if(curr.getLet() == TERMINATOR) return;
            if(curr.getRight() == null) curr.setRight(new DLBNode(TERMINATOR));
            curr = curr.getRight();
        }
    }

    public boolean contains(String key)
    {
        if(key.length() < 1) return true;
        if(head == null) return false;
        DLBNode curr = head;
        for(int i = 0; i < key.length(); i++)
        {
            char letter = key.charAt(i);
            while(curr != null)
            {
                if(curr.getLet() == letter) break;
                if(curr.getRight() == null) return false;
                curr = curr.getRight();
            }
            curr = curr.getDown();
        }
        while(curr != null)
        {
            if(curr.getLet() == TERMINATOR) return true;
            curr = curr.getRight();
        }
        return false;
    }

    public boolean containsPrefix(String key)
    {
        if(key.length() < 1) return true;
        if(head == null) return false;
        DLBNode curr = head;
        for(int i = 0; i < key.length(); i++)
        {
            char letter = key.charAt(i);
            while(curr != null)
            {
                if(curr.getLet() == letter) break;
                if(curr.getRight() == null) return false;
                curr = curr.getRight();
            }
            curr = curr.getDown();
        }
        while(curr != null)
        {
            if(curr.getLet() != TERMINATOR) return true;
            curr = curr.getRight();
        }
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
        ArrayList<String> traverse = new ArrayList<String>();
        traverseHelper(head, "", traverse);
        return traverse;
    }

    private void traverseHelper(DLBNode curr, String key, ArrayList<String> list)
    {
        while(curr != null)
        {
            if(curr.getLet() == TERMINATOR) list.add(key);
            if(curr.getDown() != null) traverseHelper(curr.getDown(), key + curr.getLet(), list);
            curr = curr.getRight();
        }
    }

    public ArrayList<String> suggest()
    {
        ArrayList<String> suggest = new ArrayList<String>();
        ArrayList<String> traverse = traverse();
        for(int i = 0; i < traverse.size(); i++)
        {
            String word = traverse.get(i);
            if(suggestHelper(word)) suggest.add(word);
            if(suggest.size() == 5) break;
        }
        return suggest;
    }

    private boolean suggestHelper(String word)
    {
        if(word.length() < searchField.length()) return false;
        for(int i = 0; i < searchField.length(); i++) if(searchField.charAt(i) != word.charAt(i)) return false;
        return true;
    }

    public int count() {return traverse().size();}
}