package com.kpi;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Random;
import java.util.Vector;

public class TravelSolution {
    private final int cityNum;
    private final int mutationPercent;
    private final int iterationsNum;
    private final int generationSize;
    private final int generationMaxSize;

    public TravelSolution(int cityNum, int iterationsNum, int mutationPercent) {
        this.cityNum = cityNum;
        this.mutationPercent = mutationPercent;
        this.iterationsNum = iterationsNum;
        generationSize = cityNum * 2;
        generationMaxSize = generationSize * 2;
    }

    public Vector<Path> findSolution() {
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

            // random crossover until get new generation (max size)
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

            // mutate given percentage
            for (int i = 0; i < generationSize / 100.0 * mutationPercent; i++)
                newGenVec.elementAt(random.nextInt(generationMaxSize)).mutate();

            startGenHashT = newGenHashT;
            startGenVec = newGenVec;

            Collections.sort(startGenVec);
            results.add(startGenVec.firstElement());
        }
        return results;
    }
}
