package com.example.moead_de;

import java.util.ArrayList;

public class Weight {

    private ArrayList<Double> weight;
    private Integer index;
    private ArrayList<Integer> neighbours;

    public double tempDistance;

    public Weight(ArrayList<Double> weight_, int index_)
    {
        this.weight = weight_;
        this.index = index_;
    }

    public ArrayList<Double> getWeight(){return this.weight;}
    private Integer getIndex(){return this.index;}

}
