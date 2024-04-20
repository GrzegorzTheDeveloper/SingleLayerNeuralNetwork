import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

public class Perceptron {


    double values[];

    static int ile = 0;
    String language;

    int count = 0;
    static double constant;

    public Perceptron(File file, double constant) throws IOException {
        values = new double[27];
        language = file.getName();
        HashMap<Character, Integer> map = Language.countCharacters(file, 1);

        for(var e:map.entrySet())
            count+= e.getValue();

        int wrt = 0;
        for(var e:map.entrySet()) {
            values[wrt++] = (double) (e.getValue()) / count;
        }
        values[26] = 0.01;

        Perceptron.constant = constant;

    }

    public void teach(int sign, Language input){
        for(int i=0; i<input.getValues().length; i++){
            values[i] += sign*constant*input.getValues()[i];
        }
        values[values.length-1] -= sign*constant;

//        System.out.println("ucze" + " " + ++ile);


    }


    public double[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return language;
    }
}
