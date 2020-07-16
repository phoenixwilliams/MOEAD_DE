package com.example.moead_de;

import java.util.ArrayList;
import java.util.Random;

public final class OperationsDE {


    public static Solution de_operator(Solution r1, Solution r2, Solution r3, double F, double CR,
                                       double lowerBound, double upperBound)
    {
        ArrayList<Double> y_genotype = new ArrayList<>();
        ArrayList<Double> r1_genotype = r1.getGenotype();
        ArrayList<Double> r2_genotype = r2.getGenotype();
        ArrayList<Double> r3_genotype = r3.getGenotype();

        double temp;

        Random random = new Random();

        for (int i=0;i<r1_genotype.size();i++)
        {
            if (random.nextDouble()<CR)
            {
                temp = r1_genotype.get(i) + F * (r2_genotype.get(i) - r3_genotype.get(i));

                if (temp > upperBound)
                {
                    temp = upperBound;
                }
                if (temp < lowerBound)
                {
                    temp = lowerBound;
                }
            }
            else
            {
                temp = r1_genotype.get(i);
            }
            y_genotype.add(temp);
        }
        return new Solution(y_genotype);
    }

    public static Double omega(double nm)
    {
        Random random = new Random();
        Double omega=0.0;
        double rand = random.nextDouble();
        if (rand<0.5)
        {
            omega = Math.pow(2.0*rand, 1/(nm+1)) - 1.0;
        }
        else
        {
            omega = 1.0 - Math.pow(2.0 - 2.0*rand, 1/(nm+1));
        }
        return omega;
    }


    public static Solution PolynomialMutation(Solution individual, double nm, double mutProb, double lowerBound,
                                                                    double upperBound)
    {
        ArrayList<Double> individualGenotype = individual.getGenotype();
        ArrayList<Double> mutatedGenotype = new ArrayList<>();
        Random random = new Random();
        double yk;

        for (int i=0;i<individualGenotype.size();i++)
        {
            if (random.nextDouble()<mutProb)
            {
                yk = individualGenotype.get(i)+omega(nm)*(upperBound-lowerBound);
            }
            else
            {
                yk = individualGenotype.get(i);
            }
            mutatedGenotype.add(yk);

        }
        return new Solution(mutatedGenotype);

    }


    public static Solution BoundedPolynomialMutation(Solution individual, double nm, double lowerBound,
                                                     double upperBound, double mutProb)
    {
        //System.out.println(nm);
        ArrayList<Double> individualGenotype = individual.getGenotype();
        ArrayList<Double> mutatedIndvGenotype = new ArrayList<>();
        Random random = new Random();

        double y,yl,yu,val,xy,mut;
        double rnd, delta1, delta2, mut_pow, deltaq;


        //System.out.println(individualGenotype.size()+":"+lowerBounds.size());
        yl = lowerBound;
        yu = upperBound;

        for (int i=0;i<individualGenotype.size();i++)
        {
            mut = random.nextDouble();

            if (mut < mutProb)
            {
                y = individualGenotype.get(i);


                delta1  = (y - yl) / (yu - yl);
                delta2  = (yu - y) / (yu - yl);
                rnd = random.nextDouble();
                mut_pow = 1.0/ (nm+1.0);

                if (rnd<=0.5)
                {
                    xy = 1.0 - delta1;
                    val = 2.0*rnd + (1.0-2.0*rnd) * Math.pow(xy, (nm+1.0));
                    deltaq = Math.pow(val, mut_pow)-1.0;
                }
                else
                {
                    xy = 1.0 - delta2;
                    val    = 2.0 * (1.0 - rnd) + 2.0 * (rnd - 0.5) * (Math.pow(xy, (nm + 1.0)));
                    deltaq = 1.0 - Math.pow(val, mut_pow);
                }

                y = y + deltaq * (yu - yl);
                if (y < yl)
                    y = yl;
                if (y > yu)
                    y = yu;
                mutatedIndvGenotype.add(y);
            }
            else
            {
                mutatedIndvGenotype.add(individualGenotype.get(i));
            }
        }
        return new Solution(mutatedIndvGenotype);
    }







}
