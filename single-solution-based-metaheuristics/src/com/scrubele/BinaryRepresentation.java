package com.scrubele;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;


public class BinaryRepresentation extends LinearRepresentation {

    public List<Integer> encoding;

    BinaryRepresentation() {
        super();
        this.encoding = new ArrayList<>();
    }

    @Override
    public final List<Number> getEncoding() {
        return new ArrayList<>(this.encoding);
    }

    @Override
    public void printEncoding() {
        super.printEncoding();
    }


    public List<Map<Integer, Float>> objectiveFunction(List<Integer> weights, List<Integer> values, int weightLimit, int maxEpoch) {
        this.encoding = initializeSolution(weights, values, weightLimit);
        int epoch = 0;
        Map<Integer, Float> searchSpace = new HashMap<>();
        Map<Integer, Float> runCurve = new TreeMap<>();
        while(epoch<maxEpoch) {
            Set<List<Integer>> candidateSolutions = generateSolutions(this.encoding);
            int weight = getConvergence(weights, this.encoding, weightLimit);
            int optimality = getOptimality(values, this.encoding);
            if(weight > -1){
                searchSpace.put(weight, (float)optimality);
                float ratio = 0;
                if (weight>0)
                    ratio = (float)optimality/weight;
                runCurve.put(epoch, ratio);

            } else {
//                runCurve.put(epoch, 0);
            }
            this.encoding = getRandomCandidateSolution(candidateSolutions);
            epoch +=1;
        }
        return Arrays.asList(searchSpace, runCurve);
    }

    public List<Integer> initializeSolution(List<Integer> weights, List<Integer> values, int weightLimit) {
        List<Integer> initialSolution = new ArrayList<>();
        for (int weight : weights) {
            if (weight < weightLimit) {
                int initialValue = Math.round((float)Math.random());
                initialSolution.add(initialValue);
            } else {
                initialSolution.add(0);
            }
        }
        return initialSolution;
    }

    public Set<List<Integer>> generateSolutions(List<Integer> initialSolution) {
        int maxDistance = initialSolution.size();
        Set<List<Integer>> candidateSolutions = new HashSet<>();
//        candidateSolutions = generateCandidateSolutionSet(candidateSolutions, initialSolution, maxDistance-1, 1);
        for(int i=0; i<initialSolution.size(); i++){
            List<Integer> currentSolution = new ArrayList<>(initialSolution);
            currentSolution = flipValueByIndex(currentSolution, i);
            candidateSolutions.add(currentSolution);
        }
        return candidateSolutions;
    }

    public  Set<List<Integer>> generateCandidateSolutionSet(Set<List<Integer>> candidateSolutions, List<Integer> previousSolution, int i, int changesLeft){
        List<Integer> currentSolution = new ArrayList<>(previousSolution);
        candidateSolutions.add(currentSolution);
        if(changesLeft == 0){
            candidateSolutions.add(previousSolution);
        }
        if (i<0) return candidateSolutions ;
        candidateSolutions.addAll(generateCandidateSolutionSet(candidateSolutions, previousSolution, i-1, changesLeft));
        previousSolution = flipValueByIndex(previousSolution, i);
        candidateSolutions.addAll(generateCandidateSolutionSet(candidateSolutions, previousSolution, i-1, changesLeft-1));
        return candidateSolutions;
    }
    public int flipValue(int value) {
        return value ^ 1;
    }
    public List<Integer> flipValueByIndex(List<Integer> previousSolution, int itemToFlip) {
        int newItemValue = flipValue(previousSolution.get(itemToFlip));
        previousSolution.set(itemToFlip, newItemValue);
        return previousSolution;
    }

    public  List<Integer> getRandomCandidateSolution(Set<List<Integer>> candidateSolutions){
        int setSize = candidateSolutions.size();
        int itemToGet = Math.round((float) Math.random()*(setSize - 1));
        List<List<Integer>> candidateSolutionsList = new ArrayList<>(candidateSolutions);
        return candidateSolutionsList.get(itemToGet);
    }

    public int getConvergence(List<Integer> weights, List<Integer> solution, int maxWeight){
        int solutionWeight = 0;
        for(int i = 0; i< solution.size(); i ++){
            int solutionItem = solution.get(i);
            if (solutionItem > 0){
                solutionWeight += weights.get(i);
            }
            if(solutionWeight>maxWeight)
                return -1;
        }
        return solutionWeight;
    }

    public int getOptimality(List<Integer> values, List<Integer> solution){
        int solutionValue = 0;
        for(int solutionItem: solution){
            if (solutionItem > 0){
                solutionValue += values.get(solutionItem);
            }
        }
        return solutionValue;
    }
    public void makePlot(Map<Integer, Float> dataCollection, String plotName, String xAxisName, String yAxisName){
        Plot plot = Plot.plot(null);
        Plot.Data data = Plot.data();
        for (Map.Entry<Integer, Float> entry : dataCollection.entrySet()) {
            data.xy(entry.getKey(), entry.getValue());
        }
        plot.series(null, data, Plot.seriesOpts().
                marker(Plot.Marker.DIAMOND).
                markerColor(Color.GREEN).
                color(Color.BLACK));

        plot.xAxis(xAxisName, null);
        plot.yAxis(yAxisName, null);
        try {
            plot.save(plotName, "png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void processTest(int i, List<Map<Integer, Float>> results){
        Map<Integer, Float> searchSpace = results.get(0);
        Map<Integer, Float> runCurve = results.get(1);
        System.out.println("Search space:" + searchSpace);
        System.out.println("Search space:" + runCurve);

        makePlot(searchSpace, "search_space"+ i, "weight", "value");
        makePlot(runCurve, "run_curve" + i, "epoch", "ratio");
    }
    public void test1(int i) {
        System.out.println("----\nBinary representation" + i + ":");
        List<Integer> values = Arrays.asList(4, 2, 2, 1, 10);
        List<Integer> weights = Arrays.asList(20, 1, 1, 2, 4);
        List<Map<Integer, Float>> results = this.objectiveFunction(weights, values, 15, 200);
        processTest(i, results);
        this.printEncoding();
    }

    public void test2(int i) {
        System.out.println("----\nBinary representation" + i + ":");
        List<Integer> values = Arrays.asList(4, 2, 2, 1);
        List<Integer> weights = Arrays.asList(10, 3, 5, 2);
        List<Map<Integer, Float>> results = this.objectiveFunction(weights, values, 15, 200);
        processTest(i, results);
        this.printEncoding();
    }

    public void test3(int i) {
        System.out.println("----\nBinary representation" + i + ":");
        List<Integer> values = Arrays.asList(4, 2, 2, 1, 3, 7);
        List<Integer> weights = Arrays.asList(10, 3, 5, 2, 4, 1);
        List<Map<Integer, Float>> results = this.objectiveFunction(weights, values, 15, 200);
        processTest(i, results);
        this.printEncoding();
    }
    public void test() {
        test1(1);
        test2(2);
        test3(3);
    }
}