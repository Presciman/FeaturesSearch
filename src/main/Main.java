
package main;

import algorithms.Classifiers;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import utils.DataReader;

/**
 *
 * @author ericsun
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scan = new Scanner(System.in);
        System.out.println("------------This is Baixi's CS205 project2, welcome------------------");
        System.out.println("-Please put dataset txt file in the datas folder and input file name: ");
        String file_choice = scan.next();
        //1.read the dataset and store the data into dynamic 2 dimension array
        String path = "datas/";
        path += file_choice;
        File file = new File(path);
        ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
        DataReader dataReader = new DataReader();
        if(file.exists())
            data = dataReader.getData(path); //read the data
        else{
            System.out.println("Invalid input of file name, please try again");
            return;
        }
        System.out.println("-Please select the algorithm: (Type in 1, 2 or 3)");
        System.out.println("1 Forward Algorithm");
        System.out.println("2 Backward Algorithm");
        System.out.println("3 My Original Algorithm");
        String algorithm_choice = scan.next();
//        String algorithm_choice = "1";
        Classifiers classifiers = new Classifiers();
        int feature_num = data.get(0).size()-1;
        int instance_num = data.size();
        System.out.println("This dataset has "+feature_num+" features (not including the class attribute), with "+instance_num+" instances");
        if(algorithm_choice.equals("1"))
        {
            classifiers.getForward(data);
        }
        else if(algorithm_choice.equals("2"))
        {
            classifiers.getBackward(data);
        }
        else if(algorithm_choice.equals("3"))
        {
            classifiers.getOriginal(data);
        }
        else{
            System.out.println("Invalid input of algorithm, please try again");
            return ;
        }
        //2.implement the forward algorithm.
        return ;
    }
    
}
