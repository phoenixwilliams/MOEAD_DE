package com.example.moead_de;

import java.util.ArrayList;
import java.util.Random;

public final class Initialize {

    public static Solution InitialSolution(int variableEncoding)
    {
        ArrayList<Double> genotype = new ArrayList<>();
        Random random = new Random();
        for (int i=0;i<variableEncoding;i++)
        {
            genotype.add(random.nextDouble());
        }
        return new Solution(genotype);
    }

    public static ArrayList<Solution> InitialPopulation(int size, int variableEncoding)
    {
        ArrayList<Solution> population = new ArrayList<>();
        for (int i=0;i<size;i++)
        {
            population.add(InitialSolution(variableEncoding));
        }
        return population;
    }

}
