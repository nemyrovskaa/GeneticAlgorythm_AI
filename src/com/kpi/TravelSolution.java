package com.kpi;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

/**
 * The TravelSolution class encapsulates the logic for solving the Traveling Salesman Problem (TSP)
 * using a Genetic Algorithm (GA). It generates an initial population of paths, iteratively improves
 * them through selection, crossover, and mutation, and keeps track of the best solutions.
 */
public class TravelSolution {
    /** Number of cities in the problem. */
    private final int cityNum;
    /** Percentage of paths to mutate in each generation. */
    private final int mutationPercent;
    /** Number of iterations to perform. */
    private final int iterationsNum;
    /** Size of the selected population for crossover. */
    private final int generationSize;
    /** Maximum size of a generation. */
    private final int generationMaxSize;

    /**
     * Constructs a TravelSolution instance with the specified parameters.
     *
     * @param cityNum           The number of cities in the TSP.
     * @param iterationsNum     The number of iterations to run the algorithm.
     * @param mutationPercent   The percentage of the population to undergo mutation per generation.
     */
    public TravelSolution(int cityNum, int iterationsNum, int mutationPercent) {
        this.cityNum = cityNum;
        this.mutationPercent = mutationPercent;
        this.iterationsNum = iterationsNum;
        generationSize = cityNum * 2;
        generationMaxSize = generationSize * 2;
    }

    /**
     * Executes the Genetic Algorithm to find an optimal solution for the TSP.
     *
     * @return A vector containing the best path found at each iteration.
     */
    public Vector<Path> findSolution() {
        // create the initial generation with
        Hashtable<Double, Path> startGenHashT = new Hashtable<>();
        Vector<Path> startGenVec = new Vector<>();
        Vector<Path> results = new Vector<>();

        // generate start generation (max size)
        int cnt = 0;
        while (startGenHashT.size() < generationMaxSize) {
            Path path = new Path(cityNum);
            if (! startGenHashT.containsKey(path.getKey())) {
                startGenHashT.put(path.getKey(), path);
                startGenVec.add(cnt++, path);
            }
        }

        int iter = 0;
        while (iter++ < iterationsNum) {
            Hashtable<Double, Path> newGenHashT = new Hashtable<>();
            Vector<Path> newGenVec = new Vector<>();

            // get the best instances from generation (size)
            Collections.sort(startGenVec);
            for (int i = generationMaxSize - 1; i >= generationSize; i--) {
                Path toRemove = startGenVec.remove(i);
                startGenHashT.remove(toRemove.getKey());
            }

            // perform crossover to create a new generation
            Random random = new Random();
            cnt = 0;
            while (newGenHashT.size() < generationMaxSize) {
                Path parentPath1 = startGenVec.elementAt(random.nextInt(generationSize));
                Path parentPath2 = startGenVec.elementAt(random.nextInt(generationSize));
                Path childPath = (Path) parentPath1.crossoverWith(parentPath2);

                if (!newGenHashT.containsKey(childPath.getKey())) {
                    newGenHashT.put(childPath.getKey(), childPath);
                    newGenVec.add(cnt++, childPath);
                }
            }

            // perform mutation on a percentage of the paths
            for (int i = 0; i < generationSize / 100.0 * mutationPercent; i++)
                newGenVec.elementAt(random.nextInt(generationMaxSize)).mutate();

            // update the current generation with the new generation
            startGenHashT = newGenHashT;
            startGenVec = newGenVec;

            // record the best path of the current generation
            Collections.sort(startGenVec);
            results.add(startGenVec.firstElement());
        }
        return results;
    }
}
