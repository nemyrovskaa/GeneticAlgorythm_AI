package com.kpi;

/**
 * Represents a chromosome in the context of a genetic algorithm.
 * A chromosome can undergo crossover and mutation operations as part of
 * the algorithm's evolutionary process.
 */
public interface Chromosome extends Comparable<Chromosome> {
    /**
     * Performs a crossover operation with another chromosome to produce an offspring.
     *
     * @param chromosome    the other chromosome to crossover with
     * @return a new Chromosome instance representing the offspring
     */
    Chromosome crossoverWith(Chromosome chromosome);

    /**
     * Applies a mutation operation to this chromosome to introduce variability.
     */
    void mutate();
}
