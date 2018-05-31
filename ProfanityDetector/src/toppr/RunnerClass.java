package toppr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RunnerClass {

    public static void main(String[] args) throws IOException {
        // findProfanityInChats();
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        boolean flag;
        flag = ProfaneDictionaryService.getInstance().isProfane(input.trim().toLowerCase());
        if (flag) {
            System.out.println("profane");
        } else {
            System.out.println("clean");
        }

        br.close();
    }
/*
    private static void findProfanityInChats(){
        String dir = "src/toppr/Topprchats";
        String filename; 
        String flag = "false" ;
        for(int i=3970; i<3987;i++){
            flag = "false";
            filename= String.valueOf(i);
            String content;
            try {
                content = new Scanner(new File(dir+"/"+filename+".txt")).useDelimiter("\\Z").next();
            } catch (FileNotFoundException ex) {
                continue;
            }
            flag = ProfaneDictionaryService.getInstance().isProfane(content.trim().toLowerCase());
            if(flag.startsWith("t")){
                System.out.println(filename+".txt has profane content " + flag);
                break;
            }
            
        }
        if(flag.startsWith("f")){
            System.out.println("All files are clean");
        }
        
    }*/
}
