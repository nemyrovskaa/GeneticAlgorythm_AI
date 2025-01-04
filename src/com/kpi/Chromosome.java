package com.kpi;

public interface Chromosome extends Comparable<Chromosome> {
    Chromosome crossoverWith(Chromosome chromosome);
    void mutate();
}
