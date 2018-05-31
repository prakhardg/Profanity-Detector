package toppr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 *
 * @author Prakhar Dev Gupta This class ensures that user cannot create multiple
 * objects of this class.
 */
public class ProfaneDictionaryService {

    Stemmer stemmer = new Stemmer();
    static ProfaneDictionaryService profaneDictionaryService;
    private final Map<String, String> profaneStemmed = new HashMap<>();
    private File dir;
//    private String profane_word;

    private ProfaneDictionaryService() {
    }               // Empty constructor of private access type ensuring non creation of class profaneDictObject outsidtionaryServiceclass

    public static ProfaneDictionaryService getInstance() {
        synchronized (ProfaneDictionaryService.class) {
            if (profaneDictionaryService == null) {
                profaneDictionaryService = new ProfaneDictionaryService();
                try {
                    profaneDictionaryService.listInitializer();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
        return profaneDictionaryService;
    }

    private void listInitializer() throws Exception {
//        String classpathstr = System.get
        dir = new File("src/toppr/profane_words.txt");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(dir)) /// Filepath is provided.
                ) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) //Creating Hashmap
            {
                profaneStemmed.put(stemmer.stem(currentLine.trim().toLowerCase()), "profane");
            }
        }
    }

    boolean isProfane(String input) {
        int N = 1;                        // Value of N in Ngrams
        int inputSize = new StringTokenizer(input, " ").countTokens();
        while (N <= Math.min(4, inputSize)) {
            Set<String> wordset = getNgrams(input, N);

            for (String word : wordset) {
                word = stemmer.stem(word.trim().toLowerCase());
                if (profaneStemmed.containsKey(word.trim())) {
//                        setProfaneWordStatus(word.trim());
                    return true;
                }

            }
            N++;
        }
        return false;
    }

    private static Set<String> getNgrams(String input, int N) {
        String[] wordsArray = input.split(" ");
        HashSet<String> ngramHashSet = new HashSet<>();
        int wordsArrayLength = wordsArray.length;
        for (int currentIndex = 0; currentIndex <= wordsArrayLength - N; currentIndex++) {
            StringBuilder stringBuilder = new StringBuilder(wordsArray[currentIndex]);
            for (int next = currentIndex + 1; next - currentIndex < N; next++) {
                stringBuilder.append("_").append(wordsArray[next]);
            }
            String ngramHashSetValue = stringBuilder.toString().trim();
            ngramHashSet.add(ngramHashSetValue);
        }

        return ngramHashSet;
    }
    /*Testing code.*/
//    private void setProfaneWordStatus(String w) {
//    profane_word = w;
//    }
//    
//    private  String getProfaneWord(){
//        return profane_word;
//    }
}
