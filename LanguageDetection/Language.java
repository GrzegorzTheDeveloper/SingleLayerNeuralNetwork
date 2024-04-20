

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Language {

    public String name;
    private double[] values = new double[26];

    int count = 0;

    public Language(File file, String name) throws IOException {
        this.name = name;
        int wrt = 0;
        for(var e : countCharacters(file).entrySet()) {
            values[wrt++] = (double) e.getValue() / count;
        }
    }

    public Language(HashMap<Character, Integer> map){
        int wrt = 0;
        for(var e : map.entrySet())
            count+=e.getValue();
        for(var e : map.entrySet()) {
            values[wrt++] = (double) e.getValue() /count;

        }
    }

    public static HashMap<Character, Integer> countCharacters(List<String> lines){


        HashMap<Character, Integer> map = new HashMap<>();
        for(int i='A'; i<'A'+26; i++){
            map.put((char) i, 0);
        }

        for(var line:lines){
            for(var c:line.toCharArray()){
                c = Character.toUpperCase(c);
                if(map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);

                }

            }
        }

        return map;
    }

    public static HashMap<Character, Integer> countCharacters(File file, int setNumber) throws IOException {
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i='A'; i<'A'+26; i++){
            map.put((char) i, 0);
        }

        List<String> lines = Files.readAllLines(file.listFiles()[setNumber].toPath(), StandardCharsets.UTF_8);
        for(var line:lines){
            for(var c:line.toCharArray()){
                c = Character.toUpperCase(c);
                if(map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                }

            }
        }

        return map;
    }

    public HashMap<Character, Integer> countCharacters(File file) throws IOException {
        HashMap<Character, Integer> map = new HashMap<>();
        for(int i='A'; i<'A'+26; i++){
            map.put((char) i, 0);
        }

        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        for(var line:lines){
            for(var c:line.toCharArray()){
                c = Character.toUpperCase(c);
                if(map.containsKey(c)) {
                    map.put(c, map.get(c) + 1);
                    count++;
                }

            }
        }

        return map;
    }

    public void train(Perceptron perceptron) {
        double sum = 0;
        String base = perceptron.language;
        for(int i = 0; i < this.values.length; ++i) {
            sum += this.values[i] * perceptron.getValues()[i];
        }

        boolean bigger = sum > perceptron.getValues()[this.values.length];
        if (bigger && base.compareTo(this.name) != 0) {
            perceptron.teach(-1, this);
        } else if (!bigger && base.compareTo(this.name) == 0) {
            perceptron.teach(1, this);
        }

    }

    public String assign(List<Perceptron> perceptrons) {



        Map<Perceptron, Double> map = new HashMap<>();

        for(var p:perceptrons) {
            double sum = 0;
            for (int i = 0; i < this.values.length; i++) {
                sum += this.values[i] * p.getValues()[i];
            }
            map.put(p, sum-p.values[values.length]);
        }

        return Collections.max(map.entrySet(), Map.Entry.comparingByValue()).getKey().toString();
    }


    public String toString() {
        return this.name;
    }

    public double[] getValues() {
        return values;
    }
}
