package com.example.moead_de;

import java.util.ArrayList;
import java.util.Collections;

public final class FitnessUtils {


    public static ArrayList<Double> evaluate(ArrayList<Double> genotype, String problem, int objectiveNum)
    {
        ArrayList<Double> fitness;

        switch (problem){
            case "ZDT1":
                fitness = ZDT.ZDT1(genotype);
                break;
            case "ZDT2":
                fitness = ZDT.ZDT2(genotype);
                break;
            case "ZDT3":
                fitness = ZDT.ZDT3(genotype);
                break;
            case "ZDT4":
                fitness = ZDT.ZDT4(genotype);
                break;
            case "ZDT6":
                fitness = ZDT.ZDT6(genotype);
                break;
            case "DTLZ1":
                //fitness = DTLZ.DTLZ1(genotype, objectiveNum);
                fitness = DTLZ.dtlz1(genotype, objectiveNum);
                break;
            case "DTLZ2":
                fitness = DTLZ.dtlz2(genotype, objectiveNum);
                break;
            case "DTLZ3":
                fitness = DTLZ.dtlz3(genotype,objectiveNum);
                break;
            case "DTLZ4":
                fitness = DTLZ.dtlz4(genotype,objectiveNum);
                break;
            default:
                throw new IllegalArgumentException("Problem not recognised");
        }
        return fitness;
    }


    public static void problemEvaluationPopulation(ArrayList<Solution> population, String problem, int objectiveNum)
    {
        ArrayList<Double> fitness;
        for (Solution sol:population)
        {
            fitness = evaluate(sol.getGenotype(), problem, objectiveNum);
            sol.setProblemFitness(fitness);
        }
    }

    public static void NormalizePopulationProblemFitness(ArrayList<Solution> population, int objectiveNum)
    {
        Double bmin,bmax, fitnessNorm;

        //empty normalized solutions
        for (Solution sol: population)
        {
            sol.initializeNormalizedFitness(objectiveNum);
        }

        for (int i=0;i<objectiveNum;i++)
        {
            Collections.sort(population, new ProblemFitnessComparator(i));

            bmin = population.get(0).getProblemFitness(i);
            population.get(0).setNormalizedProblemFitness(i, 0.0);
            bmax = population.get(population.size()-1).getProblemFitness(i);
            population.get(population.size()-1).setNormalizedProblemFitness(i, 1.0);

            for (int j=1;j<population.size()-1;j++)
            {
                fitnessNorm = (population.get(j).getProblemFitness(i)-bmin)/(bmax-bmin);
                population.get(j).setNormalizedProblemFitness(i, fitnessNorm);

            }
        }

    }

    public static Double Tchebycheff(ArrayList<Double> weight, ArrayList<Double> referenceVector,
                                     ArrayList<Double> objectiveVector)
    {
        ArrayList<Double> distances = new ArrayList<>();
        double dist;
        for (int i=0;i<referenceVector.size();i++)
        {
            dist = weight.get(i)*(Math.abs(objectiveVector.get(i)-referenceVector.get(i)));
            distances.add(dist);
        }

        return Collections.max(distances);


    }


}
