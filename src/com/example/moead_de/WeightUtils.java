package com.example.moead_de;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public final class WeightUtils {

    public static int combination(int n, int k)
    {
        if (n<k) return -1;

        double ans = 1;
        for (int i=0;i<=n;i++)
        {
            ans = ans * i;
            ans = ans / (double) (i-k);
        }
        return (int) ans;
    }
    
    /**
    public static double initialize_uniform_point(int num, int number_weight, int objectiveNum)
    {
        int layer_size;
        int column = 0;
        
        ArrayList<Double> Vec = new ArrayList<>();
        ArrayList<ArrayList<Double>> lambda;

        int gaps = 1;

        number_weight = 0;
        while(true)
        {
            layer_size = combination(objectiveNum + gaps - 1, gaps);

            if(layer_size > num)break;
            number_weight = layer_size;
            gaps = gaps + 1;
        }
        gaps = gaps - 1;
        lambda = new ArrayList<>();

        for (int i=0;i<objectiveNum;i++)
        {
            Vec.add(0.0);
        }

        for (int i=0;i<number_weight;i++)
        {
            for (int j=0;j<objectiveNum;j++)
            {
                lambda.add()
            }
        }

    }
     */


    public static double round(double value, int places)
    {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double randomRange(double rangeMin, double rangeMax)
    {
        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }


    public static ArrayList<Double> initialWeightVector(int objectivesNum)
    {

        if (objectivesNum<=1) throw new IllegalArgumentException();



        double sum = 0.0;
        double weight;
        ArrayList<Double> weightVector = new ArrayList<>();
        Random random = new Random();

        for (int i=0; i<objectivesNum;i++)
        {
            weight = random.nextDouble();
            round(weight,2);
            sum += weight;
            weightVector.add(weight);
        }
        for (int i=0;i<objectivesNum;i++)
        {
            weightVector.set(i, weightVector.get(i)/sum);
        }
        return weightVector;

    }

    public static ArrayList<ArrayList<Double>> initialWeightPopulation(int amount, int objectiveNum)
    {
        ArrayList<ArrayList<Double>> weights = new ArrayList<>();

        for (int i=0;i<amount;i++)
        {
            weights.add(initialWeightVector(objectiveNum));
        }
        return weights;
    }

    public static ArrayList<Integer> computeBi(ArrayList<Double> solnI, ArrayList<ArrayList<Double>> weights, int T)
    {
        ArrayList<Integer> bI = new ArrayList<>();
        ArrayList<Double> distances = new ArrayList<>();

        double dist;
        double largestDist;
        int largestDistIndex = -1;

        for (int i=0;i<weights.size();i++)
        {
            dist = mathUtils.euclideanDistance(solnI,weights.get(i));

            if (bI.size()<T)
            {
                bI.add(i);
                distances.add(dist);
            }
            else
            {
                largestDist = Double.NEGATIVE_INFINITY;
                for (int j=0;j<bI.size();j++)
                {
                    if (distances.get(j)>largestDist)
                    {
                        largestDist = distances.get(j);
                        largestDistIndex = j;
                    }
                }
                //System.out.println("largest dist:"+largestDist+ " dist:"+dist);
                if (dist<largestDist)
                {
                    distances.set(largestDistIndex, dist);
                    bI.set(largestDistIndex, i);
                }
            }
            //System.out.println(bI.toString());
            //System.out.println(distances);
        }

        return bI;
    }


    public static ArrayList<ArrayList<Integer>> computeB(ArrayList<ArrayList<Double>> weightPopulation, int T)
    {
        ArrayList<ArrayList<Integer>> B = new ArrayList<>();
        ArrayList<Integer> bI;
        for (int i=0;i<weightPopulation.size();i++)
        {
            bI = computeBi(weightPopulation.get(i), weightPopulation, T);
            B.add(bI);
        }

        return B;
    }

    public static ArrayList<Double> computeZ(ArrayList<Solution> population, int objectivesNum)
    {
        ArrayList<Double> z = new ArrayList<>();
        Double fitness;
        for (int i=0;i<objectivesNum;i++)
        {
            z.add(Double.POSITIVE_INFINITY);
        }

        for (Solution sol : population)
        {
            for (int i=0;i<objectivesNum;i++)
            {
                fitness = sol.getProblemFitness(i);
                if (fitness<z.get(i))
                {
                    z.set(i,fitness);
                }
            }

        }
        return z;
    }

}
