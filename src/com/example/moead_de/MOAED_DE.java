package com.example.moead_de;

import javax.sound.midi.SysexMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MOAED_DE {

    public static void moaedDE(String problem, int iterations, int N, int T, int objectiveNum, int variableEncoding,
                               double delta, double nr, double CR, double F, double lowerBound, double upperBound,
                               double nm, double pm)
    {
        //System.out.println(nm);

        // STEP 1 INITIALIZATION
        ArrayList<Integer> populationIdx = new ArrayList<>();
        for (int p=0;p<N;p++)
        {
            populationIdx.add(p);
        }

        ArrayList<ArrayList<Double>> weightVectors = WeightUtils.initialWeightPopulation(N,objectiveNum);
        ArrayList<ArrayList<Integer>> B = WeightUtils.computeB(weightVectors,T);
        ArrayList<Solution> population = Initialize.InitialPopulation(N, variableEncoding);
        FitnessUtils.problemEvaluationPopulation(population, problem, objectiveNum);
        FitnessUtils.NormalizePopulationProblemFitness(population,objectiveNum);

        ArrayList<Double> z = WeightUtils.computeZ(population, objectiveNum);
        //ArrayList<Double> z = new ArrayList<>(Arrays.asList(0.0,0.0));
        ArrayList<Double> yFitness;

        ArrayList<Integer> P = new ArrayList<>();
        Random random = new Random();
        ArrayList<Solution> tempPopulation;


        int r1,r2,r3,c,ni,tempj;
        for (int i=0;i<iterations;i++)
        {
            if (i%1000==0)
            {
                System.out.println(Double.toString((double)i/iterations));
            }
            //System.out.println("Z:"+z.toString());
            //System.out.println(i);
            // STEP 2 UPDATE

            //Selection of Mating/Update Range
            for (int j=0;j<N;j++)
            {
                P = new ArrayList<>();
                if (random.nextDouble() < delta) {
                    for (int k=0;k<B.get(j).size();k++)
                    {
                        P.add(B.get(j).get(k));
                    }

                } else {
                    for (int k=0;k<populationIdx.size();k++)
                    {
                        P.add(populationIdx.get(k));
                    }
                }


                //Reproduction
                r1 = j;
                r2 = P.get(random.nextInt(P.size()));
                r3 = P.get(random.nextInt(P.size()));
                //System.out.println(P.toString());
                //System.out.println(r1+":"+r2+":"+r3);
                //System.out.println();

                Solution y = OperationsDE.de_operator(population.get(r1), population.get(r2),
                        population.get(r3), F, CR, lowerBound, upperBound);


                y = OperationsDE.BoundedPolynomialMutation(y, nm, lowerBound, upperBound, pm);
                //y = OperationsDE.PolynomialMutation(y,nm,pm,lowerBound,upperBound);
                yFitness = FitnessUtils.evaluate(y.getGenotype(),problem,objectiveNum);
                y.setProblemFitness(yFitness);

                //System.out.println("y:"+y.getProblemFitness().toString());

                /**
                tempPopulation = new ArrayList<>();

                for (int t=0;t<population.size();t++)
                {
                    tempPopulation.add(population.get(t));
                }
                tempPopulation.add(y);
                FitnessUtils.NormalizePopulationProblemFitness(tempPopulation,objectiveNum);
                //System.out.println(y.getNormalizedFitness().toString());

                 */


                //Update Z

                //System.out.println(y.getProblemFitness().toString());
                for (int zi=0;zi<objectiveNum;zi++)
                {
                    if (z.get(zi)>y.getProblemFitness(zi))
                    {
                        z.set(zi, y.getProblemFitness(zi));
                    }
                }


                /**
                for (int zi=0;zi<objectiveNum;zi++)
                {
                    if (z.get(zi)>=y.getNormalizedFitness(zi))
                    {
                        z.set(zi, y.getNormalizedFitness(zi));
                    }
                }
                 */





                // Update of Solutions
                c = 0;
                while (c<nr)
                {

                    if (P.size()==0)
                    {
                        break;
                    }
                    tempj = random.nextInt(P.size());
                    ni = P.get(tempj);

                    if (FitnessUtils.Tchebycheff(weightVectors.get(ni),y.getProblemFitness(),z)<=
                            FitnessUtils.Tchebycheff(weightVectors.get(ni),population.get(ni).getProblemFitness(),z))
                    {
                        population.set(ni, y);
                        P.remove(tempj);
                    }


                    /**
                    if (FitnessUtils.Tchebycheff(weightVectors.get(ni),y.getNormalizedFitness(),z)<=
                            FitnessUtils.Tchebycheff(weightVectors.get(ni),population.get(ni).getNormalizedFitness(),z))
                    {
                        population.set(ni, y);
                        P.remove(tempj);
                    }
                     */

                    c+=1;
                }
            }
            //System.out.println("Z:"+z.toString());

        }
        for (Solution sol:population)
        {
            System.out.println(sol.getProblemFitness().toString());
            //System.out.println(sol.getGenotype().toString());
        }
        System.out.println("z:"+z.toString());
        AnalysisUtils.generateDatFile(population, "solutions", objectiveNum);




    }

    public static void main(String[] args)
    {

        String problem = "DTLZ4";
        int iterations = 5000;
        int N = 300;
        int T = 20;
        int objectiveNum = 3;
        int variableEncoding = 30;
        double delta = 0.9;
        double nr = 2;
        double CR = 0.5;
        double F = 0.5;
        double pm = (double) 1/N;
        double lowerBound = 0.0;
        double upperBound = 1.0;
        double nm = 0;


        moaedDE(problem,iterations,N,T,objectiveNum,variableEncoding, delta,nr,CR,F,lowerBound,upperBound, nm,pm);

        //ArrayList<ArrayList<Double>> weightVectors = WeightUtils.initialWeightPopulation(N,objectiveNum);
        //ArrayList<ArrayList<Integer>> B = WeightUtils.computeB(weightVectors,T);
        //ArrayList<Solution> population = Initialize.InitialPopulation(N, variableEncoding);
        //FitnessUtils.problemEvaluationPopulation(population, problem, objectiveNum);
        //FitnessUtils.NormalizePopulationProblemFitness(population,objectiveNum);
        //ArrayList<Double> z = WeightUtils.computeZ(population, objectiveNum);
        //System.out.println(z.toString());







    }
}
