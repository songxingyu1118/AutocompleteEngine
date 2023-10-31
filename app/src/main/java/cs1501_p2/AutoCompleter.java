package cs1501_p2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AutoCompleter implements AutoComplete_Inter
{
    private DLB DLBDictionary;
    private UserHistory UserDictionary;

    public AutoCompleter(String DLBFileName, String UserFileName)
    {
        this.DLBDictionary = new DLB();
        this.UserDictionary = new UserHistory();
        loadDLBDictionary(DLBFileName);
        loadUserDictionary(UserFileName);
    }
    public AutoCompleter(String DLBFileName)
    {
        this.DLBDictionary = new DLB();
        this.UserDictionary = new UserHistory();
        loadDLBDictionary(DLBFileName);
    }
    private void loadDLBDictionary(String fileName)
    {
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            while(input.ready()) DLBDictionary.add(input.readLine());
            input.close();
        }
        catch(IOException e) {}
    }
    private void loadUserDictionary(String fileName)
    {
        try
        {
            BufferedReader input = new BufferedReader(new FileReader(fileName));
            while(input.ready()) UserDictionary.add(input.readLine());
            input.close();
        }
        catch(IOException e) {}
    }
    public ArrayList<String> nextChar(char next)
    {
        ArrayList<String> nextChar = new ArrayList<String>();
        DLBDictionary.searchByChar(next);
        UserDictionary.searchByChar(next);
        ArrayList<String> DLBResult = DLBDictionary.suggest();
        ArrayList<String> UserResult = UserDictionary.suggest();
        for(int i = 0; i < UserResult.size(); i++)
        {
            nextChar.add(UserResult.get(i));
        }
        for(int i = 0; i < DLBResult.size(); i++)
        {
            String now = DLBResult.get(i);
            if(!nextChar.contains(now)) nextChar.add(now);
        }
        ArrayList<String> result = new ArrayList<String>();
        for(int i = 0; (i < 5) && (i < nextChar.size()); i++) result.add(nextChar.get(i));
        return result;
    }
    public void finishWord(String cur)
    {
        UserDictionary.add(cur);
    }
    public void saveUserHistory(String fname)
    {
        ArrayList<String> traverse = UserDictionary.traverse();
        try
        {
            BufferedWriter output = new BufferedWriter(new FileWriter(fname));
            for(int i = 0; i < traverse.size(); i++) output.write(traverse.get(i) + "\n");
            output.close();
        }
        catch(IOException e) {}
    }
}