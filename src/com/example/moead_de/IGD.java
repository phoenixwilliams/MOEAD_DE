package com.example.moead_de;

import com.sun.jdi.ArrayReference;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public final class IGD {


    public static Double ComputeIDG(ArrayList<Solution> population, String problem)
    {
        switch (problem){
            case "ZDT1":
                return IDG(population, "PF/ZDT1.dat");
            case "ZDT2":
                return IDG(population, "PF/ZDT2.dat");
            case "ZDT3":
                return IDG(population, "PF/ZDT3.dat");
            case "ZDT4":
                return IDG(population, "PF/ZDT4.dat");
            case "ZDT6":
                return IDG(population, "PF/ZDT6.dat");
            case "DTLZ1":
                return IDG(population, "PF/DTLZ13D.dat");
            case "DTLZ2":
                return IDG(population, "PF/DTLZ23D.dat");
            case "DTLZ3":
                return IDG(population, "PF/DTLZ33D.dat");
            case "DTLZ4":
                return IDG(population, "PF/DTLZ43D.dat");
            default:
                throw new IllegalArgumentException("Problem not recognised");
        }
    }

    public static Double EuclideanDistance(ArrayList<Double> a, ArrayList<Double> b)
    {
        double sum = 0.0;
        for (int i=0;i<a.size();i++)
        {
            sum += Math.pow(a.get(i)-b.get(i), 2.0);
        }
        sum = Math.sqrt(sum);

        return sum;
    }

    public static Double dist(ArrayList<Double> z, ArrayList<ArrayList<Double>> P)
    {
        double minDist = Double.POSITIVE_INFINITY;
        double currDist;
        for (ArrayList<Double> p: P)
        {
            currDist = EuclideanDistance(z, p);
            if (currDist<minDist)
            {
                minDist = currDist;
            }
        }
        return minDist;
    }


    public static Double IDG(ArrayList<Solution> population, String filename)
    {
        ArrayList<ArrayList<Double>> PF = loadFile(filename);
        ArrayList<ArrayList<Double>> solutions = loadPopulation(population);

        double distSum=0.0;

        for (ArrayList<Double> z: PF)
        {
            distSum += dist(z, solutions);
        }

        return distSum/PF.size();
    }


    public static ArrayList<ArrayList<Double>> loadPopulation(ArrayList<Solution> populations)
    {
        ArrayList<ArrayList<Double>> points = new ArrayList<>();

        for (Solution sol:populations)
        {
            points.add(sol.getProblemFitness());
        }

        return points;
    }


    public static ArrayList<ArrayList<Double>> loadFile(String fileLocation)
    {

        try {
            String[] lines = Files.readAllLines(new File(fileLocation).toPath()).toArray(new String[0]);
            ArrayList<ArrayList<Double>> PF = new ArrayList<>();
            ArrayList<Double> temp;
            for (String line:lines)
            {
                temp = new ArrayList<>();

                int numbersAdded = 0;

                String[] numbers = line.split("\\s+");
                for (String s:numbers)
                {
                    temp.add(Double.parseDouble(s));
                }
                PF.add(temp);
            }
            return PF;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
