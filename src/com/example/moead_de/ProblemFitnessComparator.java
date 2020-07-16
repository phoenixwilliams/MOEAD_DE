package com.example.moead_de;

import java.util.Comparator;

public class ProblemFitnessComparator implements Comparator<Solution> {

    int objective;

    public ProblemFitnessComparator(int obj){this.objective = obj;}

    @Override
    public int compare(Solution o1, Solution o2) {
        return o1.getProblemFitness(this.objective).compareTo(o2.getProblemFitness(this.objective));
    }
}
