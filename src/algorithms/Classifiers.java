/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author ericsun
 */
public class Classifiers {
    public void getForward(ArrayList<ArrayList<Double>> data)
    {
        //TODO leave one out
        ArrayList<Integer> current_list = new ArrayList<Integer>();
        double output_accuracy = 73.0;
        int index_of_nearest_neighbour = 0;
        int error_num = 0;
        double min_dist = 10;
        double treshold = 0; 
        ArrayList<Integer> features_res = new ArrayList<Integer>();
        System.out.println("Running nearest neighbour with all "+data.get(0).size()+", using 'leaving-one-out' evaluation, I get an accuracy of "+output_accuracy+"%");
        System.out.println("Beginning Search");
        for(int tree_level=1;tree_level<data.get(0).size();tree_level++)
        {
            double max_accuracy = 0;
            int feature_to_add = 0;
            ArrayList<Integer> feature_list = new ArrayList<Integer>();
            
            System.out.println();
            for(int feature_num = 1;feature_num<data.get(0).size();feature_num++)
            {//TODO find the nearest neighbour
                double accuracy = 0;
                if(!current_list.contains(feature_num)){
                error_num = 0;
                for(ArrayList testdata_row : data)
                {
                    min_dist = 10;
                    double predicted_label = -1;
                    for(ArrayList traindata_row : data)
                    {                        
                        Distance distobj = new Distance();
                        double distance=0;
                        //traindata[feature_num-1] = Double.parseDouble(traindata_row.get(feature_num).toString());
                        distance += Math.abs(Double.parseDouble(traindata_row.get(feature_num).toString()) - Double.parseDouble(testdata_row.get(feature_num).toString()));
                        for(int i=1;i<=data.get(0).size()-1;i++)
                        {
                            if(current_list.contains(i))
                            {
                                distance += Math.abs(Double.parseDouble(traindata_row.get(i).toString()) - Double.parseDouble(testdata_row.get(i).toString()));
                                  
                            }
                        }
                        if(distance < min_dist && distance != 0)
                        {
                            min_dist = distance;
                            index_of_nearest_neighbour = data.indexOf(traindata_row);
                        }
                        
//                    System.out.println("******************** predicted: "+predicted_label+" target: "+Double.parseDouble(testdata_row.get(0).toString()));

                    }
                    predicted_label = data.get(index_of_nearest_neighbour).get(0);
                    if(predicted_label != Double.parseDouble(testdata_row.get(0).toString()))
                    {
                        error_num++; 
                    }
//                System.out.println("find the nearest neighbour of "+data.indexOf(testdata_row)+" :"+index_of_nearest_neighbour+", feature number: "+feature_num+" distance is: "+min_dist);      
                } 
                accuracy = 100-((100*error_num) / data.size());
                System.out.print("Using feature(s) {");
                for(int i=0;i<current_list.size();i++)
                {
                    System.out.print(current_list.get(i)+",");
                }
                System.out.println(feature_num+"} accuracy is "+accuracy+"%");
            }
                if(max_accuracy<accuracy && !feature_list.contains(feature_num))
                {
                    max_accuracy = accuracy;
                    feature_to_add = feature_num;
                }
             
            
            }
            System.out.println();
            String out = null;
            out +="{";
            for(int i=0;i<current_list.size();i++)
            {
                out += current_list.get(i);
                out += ",";
            }
            current_list.add(feature_to_add);
            out += feature_to_add;
            out +="}";
            System.out.println("Feature set "+out+" was best, accuracy is:"+max_accuracy+"%");
            if(treshold<max_accuracy)
            {
                treshold = max_accuracy;
                features_res.add(feature_to_add);    
            }
            else
            {
                System.out.println("(Warning, Accuracy has decreased! Continuing search in case of local maxima)");
            }
        }
        System.out.print("Finished search!! The best feature subset is ");
        String out1 = null;
        out1 += "{";
        for(int o=0;o<features_res.size();o++)
        {
           out1 += features_res.get(o);
           if(o < features_res.size()-1)
               out1 += ",";
        }
        out1 += "}";
        System.out.print(out1+", which has an accuracy of "+treshold+"%");     
    }
    
    public void getBackward(ArrayList<ArrayList<Double>> data)
    {
       
        //TODO leave one out
        ArrayList<Double> distance_list= new ArrayList<Double>();
        ArrayList<Integer> current_list = new ArrayList<Integer>();
        for(int init=1;init<data.get(0).size();init++)
        {
            current_list.add(init);
        }
        int features = data.get(0).size()-1;//the first col is the label, not features
        int index_of_nearest_neighbour = 0;
        int error_num = 0;
        double min_dist = 10;
        double treshold = 0; 
        ArrayList<Integer> features_res = new ArrayList<Integer>();
        HashMap<Double,ArrayList<Integer>> res_hashmap = new HashMap<Double,ArrayList<Integer>>();
        for(int tree_level=1;tree_level<data.get(0).size();tree_level++)
        {           
            System.out.println("on the "+tree_level+" level of the search tree");
            double max_accuracy = 0;
            int feature_to_add = 1;
            ArrayList<Integer> feature_list = new ArrayList<Integer>();
            
            
            for(int feature_num = 1;feature_num<data.get(0).size();feature_num++)
            {//TODO find the nearest neighbour
                double accuracy = 0;
                if(current_list.contains(feature_num)){
                error_num = 0;
                double[] testdata = new double[feature_num];
                for(ArrayList testdata_row : data)
                {
                    min_dist = 10;
                    double predicted_label = -1;
                    double[] traindata = new double[feature_num];
                    testdata[feature_num-1] = Double.parseDouble(testdata_row.get(feature_num).toString());
                    for(ArrayList traindata_row : data)
                    {                        
                        Distance distobj = new Distance();
                        double distance=0;
                        //traindata[feature_num-1] = Double.parseDouble(traindata_row.get(feature_num).toString());
                        distance -= Math.abs(Double.parseDouble(traindata_row.get(feature_num).toString()) - testdata[feature_num-1]);
                        for(int i=1;i<=data.get(0).size()-1;i++)
                        {
                            if(current_list.contains(i))
                            {
                                distance += Math.abs(Double.parseDouble(traindata_row.get(i).toString()) - Double.parseDouble(testdata_row.get(i).toString()));
                                  
                            }
                        }
                        if(distance < min_dist && distance != 0)
                        {
                            min_dist = distance;
                            index_of_nearest_neighbour = data.indexOf(traindata_row);
                        }
                        
//                    System.out.println("******************** predicted: "+predicted_label+" target: "+Double.parseDouble(testdata_row.get(0).toString()));

                    }
                    predicted_label = data.get(index_of_nearest_neighbour).get(0);
                    if(predicted_label != Double.parseDouble(testdata_row.get(0).toString()))
                    {
                        error_num++; 
                    }
//                System.out.println("find the nearest neighbour of "+data.indexOf(testdata_row)+" :"+index_of_nearest_neighbour+", feature number: "+feature_num+" distance is: "+min_dist);      
                }
                accuracy = 100-((100*error_num) / data.size());
            }
                if(max_accuracy<accuracy && !feature_list.contains(feature_num))
                {
                    max_accuracy = accuracy;
                    feature_to_add = feature_num;
                }
                         
            }
            System.out.print("Features: ");
            for(int m=0;m<current_list.size();m++)
            {
                System.out.print(current_list.get(m)+" ");
            }
            System.out.println();
            int pos = current_list.indexOf(feature_to_add);
            current_list.remove(pos);
            System.out.println("Consider feature to remove on this level: "+feature_to_add+" , the accuracy is:"+max_accuracy);
            res_hashmap.put(max_accuracy, current_list);
            if(treshold<max_accuracy)
            {
                treshold = max_accuracy;
                features_res.add(feature_to_add);
            }
        }

    }
    public void getOriginal(ArrayList<ArrayList<Double>> data)
    {
        data = refine(data);
        //TODO leave one out
        ArrayList<Integer> current_list = new ArrayList<Integer>();
        double output_accuracy = 73.0;
        int index_of_nearest_neighbour = 0;
        int error_num = 0;
        double min_dist = 10;
        double treshold = 0; 
        ArrayList<Integer> features_res = new ArrayList<Integer>();
        System.out.println("Running nearest neighbour with all "+data.get(0).size()+", using 'leaving-one-out' evaluation, I get an accuracy of "+output_accuracy+"%");
        System.out.println("Beginning Search");
        for(int tree_level=1;tree_level<data.get(0).size();tree_level++)
        {
            double max_accuracy = 0;
            int feature_to_add = 0;
            ArrayList<Integer> feature_list = new ArrayList<Integer>();
            System.out.println();
            int error_of_last_iteration = 0;
            for(int feature_num = 1;feature_num<data.get(0).size();feature_num++)
            {//TODO find the nearest neighbour
                double accuracy = 0;
                if(!current_list.contains(feature_num)){
                    error_num = 0;
                    for(ArrayList testdata_row : data)
                    {
                        min_dist = 10;
                        double predicted_label = -1;
                        for(ArrayList traindata_row : data)
                        {                        
                            Distance distobj = new Distance();
                            double distance=0;
                            distance += Math.abs(Double.parseDouble(traindata_row.get(feature_num).toString()) - Double.parseDouble(testdata_row.get(feature_num).toString()));
                            for(int i=1;i<=data.get(0).size()-1;i++)
                            {
                                if(current_list.contains(i))
                                {
                                    distance += Math.abs(Double.parseDouble(traindata_row.get(i).toString()) - Double.parseDouble(testdata_row.get(i).toString()));    
                                }
                            }
                            if(distance < min_dist && distance != 0)
                            {
                                min_dist = distance;
                                index_of_nearest_neighbour = data.indexOf(traindata_row);
                            }
                        }
                        predicted_label = data.get(index_of_nearest_neighbour).get(0);
                        if(error_num<= error_of_last_iteration && predicted_label != Double.parseDouble(testdata_row.get(0).toString()))
                        {
                            error_num++; 
                        }
                        else{
                            break;
                        }
                    } 
                    accuracy = 100-((100*error_num) / data.size());
                    error_of_last_iteration = error_num;
                    System.out.print("Using feature(s) {");
                    for(int i=0;i<current_list.size();i++)
                    {
                        System.out.print(current_list.get(i)+",");
                    }
                    System.out.println(feature_num+"} accuracy is "+accuracy+"%");
                }
                if(max_accuracy<accuracy && !feature_list.contains(feature_num))
                {
                    max_accuracy = accuracy;
                    feature_to_add = feature_num;
                }                         
            }
            System.out.println();
            String out = null;
            out +="{";
            for(int i=0;i<current_list.size();i++)
            {
                out += current_list.get(i);
                out += ",";
            }
            current_list.add(feature_to_add);
            out += feature_to_add;
            out +="}";
            System.out.println("Feature set "+out+" was best, accuracy is:"+max_accuracy+"%");
            if(treshold<max_accuracy)
            {
                treshold = max_accuracy;
                features_res.add(feature_to_add);    
            }
            else
            {
                data = refine(data);
                System.out.println("(Warning, Accuracy has decreased! Continuing search in case of local maxima)");
            }
        }
        System.out.print("Finished search!! The best feature subset is ");
        String out1 = null;
        out1 += "{";
        for(int o=0;o<features_res.size();o++)
        {
           out1 += features_res.get(o);
           if(o < features_res.size()-1)
               out1 += ",";
        }
        out1 += "}";
        System.out.print(out1+", which has an accuracy of "+treshold+"%");
    }
    
    public ArrayList<ArrayList<Double>> refine(ArrayList<ArrayList<Double>> data){
        ArrayList<ArrayList<Double>> rand_data = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> new_data = new ArrayList<ArrayList<Double>>();
        rand_data = randomList(data);
        int size = (4*rand_data.size())/5;
        for(int i=0;i<size;i++)
        {
            new_data.add(rand_data.get(i));
        }
        return new_data;
    }
    public static <V> ArrayList<V> randomList(ArrayList<V> sourceList){
    	if (sourceList.isEmpty()) {
            return sourceList;
        }
    	
    	ArrayList<V> randomList = new ArrayList<V>( sourceList.size( ) );
    	do{
    		int randomIndex = Math.abs( new Random( ).nextInt( sourceList.size() ) );
        	randomList.add( sourceList.remove( randomIndex ) );
    	}while( sourceList.size( ) > 0 );
    	
    	return randomList;
    }

}
