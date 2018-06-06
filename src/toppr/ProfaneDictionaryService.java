package toppr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import org.json.JSONObject;

/**
 *
 * @author Prakhar Dev Gupta This class ensures that user cannot create multiple
 * objects of this class.
 */
public class ProfaneDictionaryService {

    static ProfaneDictionaryService profaneDictionaryService;
    Stemmer stemmer = new Stemmer();
    private final String urlString = "http://res.cloudinary.com/dzbqhaluy/raw/upload/v1528264211/profane_wordsV4.json";
    private JSONObject profaneDict;

    private ProfaneDictionaryService() {
    }

    public static ProfaneDictionaryService getInstance() {
        synchronized (ProfaneDictionaryService.class) {
            if (profaneDictionaryService == null) {
                profaneDictionaryService = new ProfaneDictionaryService();
                try {
                    profaneDictionaryService.initValue();
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }
        return profaneDictionaryService;
    }

    private void initValue() throws Exception {

        try (InputStream inputStream = new URL(urlString).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String textFromCDN = readAll(reader);
            profaneDict = new JSONObject(textFromCDN);
        }
    }

    public boolean isProfane(String input) {
        int N = 1; // Value of N in Ngrams
        int inputSize = new StringTokenizer(input, " ").countTokens();
        while (N <= Math.min(4, inputSize)) {
            Set< String> wordset = getNgrams(input, N);

            if (wordset.stream().map((word) -> stemmer.stem(word.trim()
                    .toLowerCase()))
                    .anyMatch((word) -> (profaneDict.has(word)))) {
                return true;
            }
            N += 1;
        }
        return false;
    }

    private static Set< String> getNgrams(String input, int N) {
        String[] wordsArray = input.split("[\\s\\-\\.\\'\\?\\,\\_\\@\\!]+");
        HashSet< String> ngramHashSet = new HashSet<>();
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

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
