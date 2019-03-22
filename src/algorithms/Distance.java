/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

/**
 *
 * @author ericsun
 */
public class Distance {
    public double getEuclideanDistance(double[] vector1, double[] vector2){
        double distance = 0;
        if (vector1.length == vector2.length) 
        {
            for (int i = 0; i < vector1.length; i++) 
            {
		double temp = Math.pow((vector1[i] - vector2[i]), 2);
		distance += temp;
            }
            distance = Math.abs(Math.sqrt(distance));
	}

        return distance;
    }
    
}
