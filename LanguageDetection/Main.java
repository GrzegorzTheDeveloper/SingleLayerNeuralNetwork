import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;


public class Main {

    public static void main(String[] args) {


        double constant = 0.001;


        List<Perceptron> perceptrons = new ArrayList<>();


        File[] files = new File("C:\\Users\\grzes\\Desktop\\NAI3\\src\\languages").listFiles();


        for (var f : files) {
            try {
                perceptrons.add(new Perceptron(f, constant));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


//        List<Language> languages = new ArrayList<>();
        Language[] languages = new Language[27];

        int tmp = 0;
        for (var file : files) {
            for (int i = 1; i < 10; i++) {
                try {
                    languages[tmp++] = (new Language(file.listFiles()[i], file.getName()));
//                    System.out.println(file.getName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

//        System.out.println(Arrays.toString(perceptrons.get(1).getValues()) + "\n" + Arrays.toString(languages[9].getValues()));






        for (int i = 0; i < 100; i++) {
//            int rand = (int) (Math.random() * languages.size());
            for (var l:languages) {
                for (var p : perceptrons) {
                    l.train(p);
                }
            }
        }




        System.out.println("Podaj tekst do rozpoznania: ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> lines = new ArrayList<>();
        String line;
        while (true) {
            try {
                if ((line = br.readLine()) == null || line.isEmpty()) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            lines.add(line);
        }

        System.out.println(new Language(Language.countCharacters(lines)).assign(perceptrons));


    }
}
