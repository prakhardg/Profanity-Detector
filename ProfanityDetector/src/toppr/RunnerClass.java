package toppr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class RunnerClass {
   
    public static void main(String[] args) throws IOException {
        ProfaneDictionary pd =  new ProfaneDictionary();
        try {
            pd.listInitializer();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        boolean flag;
        flag = pd.isProfane(input.trim().toLowerCase());
        
        
        if(flag){
            System.out.println("Sentence contains profanity");
        }
        else{
            System.out.println("Sentence is clean");
        }
        
        br.close();
    }
}