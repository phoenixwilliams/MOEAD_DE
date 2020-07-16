package com.example.moead_de;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Solution {

    private ArrayList<Double> genotype;
    private ArrayList<Double> problemFitness;
    private ArrayList<Double> normalizedProblemFitness;

    public Solution(ArrayList<Double> genotype)
    {
        this.genotype=genotype;
    }

    public ArrayList<Double> getGenotype(){return this.genotype;}

    public void setProblemFitness(ArrayList<Double> fitness_){this.problemFitness = fitness_;}
    public ArrayList<Double> getProblemFitness(){return this.problemFitness;}
    public Double getProblemFitness(int i){return this.problemFitness.get(i);}
    public Double getNormalizedFitness(int i){return this.normalizedProblemFitness.get(i);}
    public ArrayList<Double> getNormalizedFitness(){return this.normalizedProblemFitness;}

    public void setNormalizedProblemFitness(int i, Double f){this.normalizedProblemFitness.set(i,f);}




    public void initializeNormalizedFitness(int objectiveNum)
    {
        this.normalizedProblemFitness = new ArrayList<>();
        for (int i=0;i<objectiveNum;i++)
        {
            this.normalizedProblemFitness.add(0.0);
        }
    }


}
