package toppr;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import java.util.Set;
import java.util.StringTokenizer;

public class ProfaneDictionary {
    
    static Stemmer  stemmer = new Stemmer();
    private BufferedReader  br =null;
    private final  Map<String , String> profaneStemmed = new HashMap<>();
    InputStreamReader is;
    
    
    public void listInitializer() throws Exception       /// Creates Stem of words and the Hashmap of Stemmed words
    {
        br = new BufferedReader(new InputStreamReader(ProfaneDictionary.class.getResourceAsStream("bad_words.txt"),"UTF-8"));                          /// Filepath is provided.
        
        String currentLine;
        while((currentLine = br.readLine())!= null)        //Creating Hashmap
            profaneStemmed.put(stemmer.stem(currentLine.trim().toLowerCase()), "profane");
        br.close();
    }

    boolean isProfane(String input)
    {
        int N=1;                        // Value of N in Ngrams
        int inputSize = new StringTokenizer(input," ").countTokens();
        final long start,end;
        start = System.nanoTime();
        while(N<=Math.min(4, inputSize))
        {
                Set<String> wordset = getNgrams(input, N);
                for(String word: wordset)
                {
                    word = stemmer.stem(word.trim().toLowerCase());
                    if(profaneStemmed.containsKey(word.trim())){
                        return true;
                    }
                          
                }
                N++;
        }
        end = System.nanoTime();
        System.out.println(end-start);
        return false;
    }

    private static Set<String> getNgrams(String input, int N) {
        String[] wordsArray  = input.split(" ");
        HashSet<String> ans = new HashSet<>();
        int s = wordsArray.length;
        for(int cur = 0; cur<= s-N; cur++){
            StringBuilder sb  = new StringBuilder(wordsArray[cur]);
            for(int next = cur+1; next-cur<N;next++){
                sb.append("_").append(wordsArray[next]);
            }
            ans.add(sb.toString().trim());
        }
        return ans;
    }
}
