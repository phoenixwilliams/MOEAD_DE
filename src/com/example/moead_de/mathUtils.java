package com.example.moead_de;

import java.util.ArrayList;

public final class mathUtils {

    public static double euclideanDistance(ArrayList<Double> a, ArrayList<Double> b)
    {
        double sum = 0.0;
        for (int i=0;i<a.size();i++)
        {
            sum += Math.pow((a.get(i)-b.get(i)),2);

        }
        return Math.sqrt(sum);
    }

}
