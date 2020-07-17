package com.example.moead_de;

import java.util.ArrayList;

public final class DTLZ {


    public static ArrayList<Double> dtlz1(ArrayList<Double> genotype, int objectiveNum)
    {
        if (objectiveNum<3)throw new IllegalArgumentException("objective number must be > 2 for dtlz1");

        ArrayList<Double> fitness = new ArrayList<>();

        //Calculate G
        int k = genotype.size()-objectiveNum+1;
        double g = 0.0;
        for (int i=genotype.size()-k;i<genotype.size();i++)
        {
            g += Math.pow(genotype.get(i)-0.5, 2.0) - Math.cos(20*Math.PI*(genotype.get(i)-0.5));
        }
        g = 100.0*(k + g);

        // Calculate f1
        double f1 = 1.0;
        for (int i=0;i<objectiveNum-1;i++)
        {
            f1*=genotype.get(i);
        }
        f1 = 0.5*f1*(1+g);

        //Calculate 2->M-1 Objectives
        ArrayList<Double> restObjectives = new ArrayList<>();
        double fn;
        for (int i=1;i<objectiveNum-2;i++)
        {
            fn = 1.0;
            for (int j=0; j<objectiveNum-2;j++)
            {
                fn *= genotype.get(j);
            }
            fn = 0.5*fn*(1.0-genotype.get(objectiveNum-2))*(1.0+g);
            restObjectives.add(fn);
        }

        //Calculate fM-1
        double fm_1 = 0.5*genotype.get(0)*(1.0-genotype.get(1))*(1.0+g);

        //Calculate fm
        double fM = 0.5*(1.0-genotype.get(0))*(1.0+g);

        fitness.add(f1);

        for (Double d:restObjectives)
        {
            fitness.add(d);
        }
        fitness.add(fm_1);
        fitness.add(fM);

        return fitness;


    }

    /**
    public static ArrayList<Double> dtlz2(ArrayList<Double> genotype, int objectiveNum)
    {
        int aux;
        double gx;

        gx = 0.0;
        int k = genotype.size() - objectiveNum - 1;
        ArrayList<Double> fitness = new ArrayList<>();

        for (int i=genotype.size()-k;i < genotype.size();i++)
        {
            gx += Math.pow(genotype.get(i) - 0.5, 2.0);
        }

        for (int i=0; i<objectiveNum;i++)
        {
            fitness.add(1.0+gx);
        }

        for (int i=0; i < objectiveNum; i++)
        {
            for (int j=0; j<objectiveNum - (i+1);j++)
            {
                fitness.set(i,fitness.get(i)*Math.cos(Math.PI * 0.5 * genotype.get(j)));
                if (i!=0)
                {
                    aux = objectiveNum - (i+1);
                    fitness.set(i, fitness.get(i)*Math.sin(Math.PI*0.5*genotype.get(aux)));
                }
            }
        }
        return fitness;

    }
     */


    public static ArrayList<Double> dtlz2(ArrayList<Double> genotype, int objectiveNum)
    {
        //Calculate g
        double g=0.0;
        int k = genotype.size()-objectiveNum+1;

        for (int i=genotype.size()-k;i<genotype.size();i++)
        {
            g += Math.pow(genotype.get(i)-0.5, 2.0);
        }

        //Calculate f1&f2
        double f1 = 1.0;
        for (int i=0; i<objectiveNum-2;i++)
        {
            f1 *= Math.cos(genotype.get(i)*(Math.PI/2.0));
        }
        double f2 = (1.0+g)*f1*Math.sin(genotype.get(objectiveNum-2)*(Math.PI/2.0));
        f1 = (1.0+g)*f1*Math.cos(genotype.get(objectiveNum-2)*(Math.PI/2.0));


        //Calculate Rest of Objectives
        ArrayList<Double> restObjectives = new ArrayList<>();
        double objective;
        for (int i=2;i<objectiveNum-1;i++)
        {
            objective = (1.0+g)*Math.cos(genotype.get(0)*(Math.PI/2.0));
            for (int j=1;j<objectiveNum-2;j++)
            {
                objective*=Math.sin(genotype.get(j)*(Math.PI/2.0));
                /**
                if (j==objectiveNum-3)
                {
                    objective*=Math.sin(genotype.get(j)*(Math.PI/2.0));
                }
                else {
                    objective *= Math.cos(genotype.get(j) * (Math.PI / 2.0));
                }
                 */
            }
            restObjectives.add(objective);
        }

        //Calculate fM
        double fM = (1.0+g)*Math.sin(genotype.get(0)*(Math.PI/2.0));

        ArrayList<Double> fitness = new ArrayList<>();
        fitness.add(f1);
        fitness.add(f2);

        for (Double d:restObjectives)
        {
            fitness.add(d);
        }

        fitness.add(fM);
        return fitness;
    }


    public static ArrayList<Double> dtlz3(ArrayList<Double> genotype, int objectiveNum)
    {
        //Calculate g
        double g=0.0;
        int k = genotype.size()-objectiveNum+1;
        for (int i=genotype.size()-k;i<genotype.size();i++)
        {
            g += Math.pow(genotype.get(i)-0.5, 2.0) - Math.cos(20*Math.PI*(genotype.get(i)-0.5));
        }
        g = 100.0*(k + g);

        //Calculate f1&f2
        double f1 = 1.0;
        for (int i=0; i<objectiveNum-2;i++)
        {
            f1 *= Math.cos(genotype.get(i)*(Math.PI/2.0));
        }
        double f2 = (1.0+g)*f1*Math.sin(genotype.get(objectiveNum-2)*(Math.PI/2.0));
        f1 = (1.0+g)*f1*Math.cos(genotype.get(objectiveNum-2)*(Math.PI/2.0));


        //Calculate Rest of Objectives
        ArrayList<Double> restObjectives = new ArrayList<>();
        double objective;
        for (int i=2;i<objectiveNum-1;i++)
        {
            objective = (1.0+g)*Math.cos(genotype.get(0)*(Math.PI/2.0));
            for (int j=1;j<objectiveNum-2;j++)
            {
                objective*=Math.sin(genotype.get(j)*(Math.PI/2.0));
            }
            restObjectives.add(objective);
        }

        //Calculate fM
        double fM = (1.0+g)*Math.sin(genotype.get(0)*(Math.PI/2.0));

        ArrayList<Double> fitness = new ArrayList<>();
        fitness.add(f1);
        fitness.add(f2);

        for (Double d:restObjectives)
        {
            fitness.add(d);
        }

        fitness.add(fM);
        return fitness;
    }

    /**
    public static ArrayList<Double> dtlz4(ArrayList<Double> genotype, int objectiveNum)
    {
        int alpha = 1;
        //Calculate g
        double g=0.0;
        int k = genotype.size()-objectiveNum+1;
        for (int i=genotype.size()-k;i<genotype.size();i++)
        {
            g += Math.pow(genotype.get(i)-0.5, 2.0);
        }
        g = 100.0*(k + g);

        //Calculate f1&f2
        double f1 = 1.0;
        for (int i=0; i<objectiveNum-2;i++)
        {
            f1 *= Math.cos(Math.pow(genotype.get(i),alpha)*(Math.PI/2.0));
        }
        double f2 = (1.0+g)*f1*Math.sin(Math.pow(genotype.get(objectiveNum-2),alpha)*(Math.PI/2.0));
        f1 = (1.0+g)*f1*Math.cos(Math.pow(genotype.get(objectiveNum-2),alpha)*(Math.PI/2.0));


        //Calculate Rest of Objectives
        ArrayList<Double> restObjectives = new ArrayList<>();
        double objective;
        for (int i=2;i<objectiveNum-1;i++)
        {
            objective = (1.0+g)*Math.cos(Math.pow(genotype.get(0),alpha)*(Math.PI/2.0));
            for (int j=1;j<objectiveNum-2;j++)
            {
                objective*=Math.sin(Math.pow(genotype.get(j),alpha)*(Math.PI/2.0));
            }
            restObjectives.add(objective);
        }

        //Calculate fM
        double fM = (1.0+g)*Math.sin(Math.pow(genotype.get(0),alpha)*(Math.PI/2.0));

        ArrayList<Double> fitness = new ArrayList<>();
        fitness.add(f1);
        fitness.add(f2);

        for (Double d:restObjectives)
        {
            fitness.add(d);
        }

        fitness.add(fM);
        return fitness;
    }
     */
    public static ArrayList<Double> dtlz4(ArrayList<Double> genotype, int objectiveNum)
    {
        double alpha = 100.0;
        //Calculate g
        double g=0.0;
        int k = genotype.size()-objectiveNum+1;

        for (int i=genotype.size()-k;i<genotype.size();i++)
        {
            g += Math.pow(genotype.get(i)-0.5, 2.0);
        }

        //Calculate f1&f2
        double f1 = 1.0;
        for (int i=0; i<objectiveNum-2;i++)
        {
            f1 *= Math.cos(Math.pow(genotype.get(i),alpha)*(Math.PI/2.0));
        }
        double f2 = (1.0+g)*f1*Math.sin(Math.pow(genotype.get(objectiveNum-2),alpha)*(Math.PI/2.0));
        f1 = (1.0+g)*f1*Math.cos(Math.pow(genotype.get(objectiveNum-2),2.0)*(Math.PI/2.0));


        //Calculate Rest of Objectives
        ArrayList<Double> restObjectives = new ArrayList<>();
        double objective;
        for (int i=2;i<objectiveNum-1;i++)
        {
            objective = (1.0+g)*Math.cos(Math.pow(genotype.get(0),2.0)*(Math.PI/2.0));
            for (int j=1;j<objectiveNum-2;j++)
            {
                objective*=Math.sin(Math.pow(genotype.get(j),2.0)*(Math.PI/2.0));
            }
            restObjectives.add(objective);
        }

        //Calculate fM
        double fM = (1.0+g)*Math.sin(Math.pow(genotype.get(0),alpha)*(Math.PI/2.0));

        ArrayList<Double> fitness = new ArrayList<>();
        fitness.add(f1);
        fitness.add(f2);

        for (Double d:restObjectives)
        {
            fitness.add(d);
        }

        fitness.add(fM);
        return fitness;
    }
}
