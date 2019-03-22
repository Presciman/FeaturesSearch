/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ericsun
 */
public class DataReader {
    public ArrayList<ArrayList<Double>> getData(String path) throws IOException{
        
        ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
        //TODO open txt file
        File file = new File(path);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String input = null;        //Use String to get the data
        while((input = bufferedReader.readLine()) != null)
        {
            ArrayList<Double> row = new ArrayList<Double>();
            String[] row_str = input.split(" ");//seperate input row to each element
            for(int i=0 ; i<row_str.length ; i++)
            {
                if(row_str[i] != " " && !row_str[i].isEmpty())
                {
                    Double this_double = Double.parseDouble(row_str[i]);//String to double
                    row.add(this_double);                    
                }
            }
            data.add(row);
        }
        bufferedReader.close();
        return data;
    }
    
}
