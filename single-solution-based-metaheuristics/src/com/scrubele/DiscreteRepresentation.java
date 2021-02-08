package com.scrubele;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class DiscreteRepresentation extends LinearRepresentation {

    public List<Integer> encoding;

    DiscreteRepresentation() {
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

    public List<Map<Integer, Float>> objectiveFunction(List<Integer> weights, List<Integer> values, int weightLimit, int solutionSetSize, int maxEpoch) {
        this.encoding = initializeSolution(weights, values, solutionSetSize, weightLimit);
        int epoch = 0;
        Map<Integer, Float> searchSpace = new TreeMap<>();
        Map<Integer, Float> runCurve = new TreeMap<>();
        while (epoch < maxEpoch) {
            Set<List<Integer>> candidateSolutions = generateSolutions(this.encoding, solutionSetSize, weights.size());
            int weight = getConvergence(weights, this.encoding, weightLimit);
            int optimality = getOptimality(values, this.encoding);
            if (weight > -1) {
                searchSpace.put(weight, (float) optimality);
                float ratio = 0;
                if (weight > 0)
                    ratio = (float) optimality / weight;
                runCurve.put(epoch, ratio);

            } else {
//                runCurve.put(epoch, 0);
            }
            this.encoding = getRandomCandidateSolution(candidateSolutions);
            epoch += 1;
        }
        return Arrays.asList(searchSpace, runCurve);
    }

    public List<Integer> initializeSolution(List<Integer> weights, List<Integer> values, int solutionSetNumber, int weightLimit) {
        int symbolNumber = weights.size() - 1;
        List<Integer> initialSolution = new ArrayList<>();
        for (int i = 0; i < solutionSetNumber; i++) {
            boolean isWeightLessThanLimit = false;
            int weight = 0;
            while (!isWeightLessThanLimit) {
                int item = Math.round((float) Math.random() * symbolNumber);
                weight = weights.get(item);
                if (weight < weightLimit) {
                    isWeightLessThanLimit = true;
                }
            }
            initialSolution.add(weight);

        }
        return initialSolution;
    }


    public Set<List<Integer>> generateSolutions(List<Integer> initialSolution, int symbolNumber, int solutionSetSize) {
        List<Integer> array = IntStream.rangeClosed(0, solutionSetSize).boxed().collect(Collectors.toList());
        List<Integer> data = new ArrayList<>(Collections.nCopies(solutionSetSize, 0));
        Set<List<Integer>> candidateSolutions = new HashSet<>();
        for(int i=0; i<initialSolution.size(); i++){
            List<Integer> currentSolution = new ArrayList<>(initialSolution);
            currentSolution = flipValueByIndex(currentSolution, i, initialSolution.size());
            candidateSolutions.add(currentSolution);
        }
//        candidateSolutions = generateCandidateSolutionSet(candidateSolutions, array, data, symbolNumber, 0, 0, solutionSetSize-1);
        return candidateSolutions;
    }

static Set<List<Integer>> generateCandidateSolutionSet(Set<List<Integer>> candidateSolutions, List<Integer> symbols, List<Integer> data, int r, int index, int start, int end)
    {
        if (index == r)
        {
            List<Integer> solution = new ArrayList<>();
            for (int j=0; j<r; j++) {
                solution.add(symbols.get(data.get(j)));
            }
            candidateSolutions.add(solution);
            return candidateSolutions;
        }
        for (int i = start; i <= end; i++) {
            data.set(index, i);
            candidateSolutions.addAll(generateCandidateSolutionSet(candidateSolutions, symbols, data, r, index + 1,
                    i, end));
        }
        return candidateSolutions;
    }
    static void printCombination()
    {
        int solutionSetSize = 5;
        int symbolNumber = 3;
        List<Integer> array = IntStream.rangeClosed(0, solutionSetSize).boxed().collect(Collectors.toList());
        List<Integer> data =  new ArrayList<Integer>(Collections.nCopies(solutionSetSize, 0));
        Set<List<Integer>> candidateSolutions = new HashSet<>();
        candidateSolutions = generateCandidateSolutionSet(candidateSolutions, array, data, symbolNumber, 0, 0, solutionSetSize-1);
        System.out.println(candidateSolutions);
    }

    public int flipValue(int value, int setSize) {
        int flippedValue = value;
        while(flippedValue==value){
            flippedValue = Math.round((float) Math.random()*(setSize - 1));
        }
        return flippedValue;
    }
    public List<Integer> flipValueByIndex(List<Integer> previousSolution, int itemToFlip, int setSize) {
        int newItemValue = flipValue(previousSolution.get(itemToFlip), setSize);
//        System.out.println(itemToFlip+ " "+ newItemValue);
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
                solutionWeight += weights.get(i)*solutionItem;
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
                solutionValue += solutionItem;
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
    public void makePlot1(Map<Long, Integer> dataCollection, String plotName, String xAxisName, String yAxisName){
        Plot plot = Plot.plot(null);
        Plot.Data data = Plot.data();
        for (Map.Entry<Long, Integer> entry : dataCollection.entrySet()) {
            data.xy(entry.getKey(), entry.getValue());
        }
        plot.series(null, data, Plot.seriesOpts().
                marker(Plot.Marker.DIAMOND).
                markerColor(Color.GREEN).
                color(Color.BLACK));

        plot.xAxis(xAxisName, null);
        plot.yAxis(yAxisName, Plot.axisOpts().range(0,5));
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
        System.out.println("Run curve:" + runCurve);

        makePlot(searchSpace, "search_space"+ i, "weight", "value");
        makePlot(runCurve, "run_curve" + i, "epoch", "ratio");
    }
    public long test1(int i) {
        long startTime = System.nanoTime();
        System.out.println("----\nBinary representation" + i + ":");
        List<Integer> values = Arrays.asList(4, 2, 2, 1, 10);
        List<Integer> weights = Arrays.asList(1, 2, 3, 10, 4);
        List<Map<Integer, Float>> results = this.objectiveFunction(weights, values, 105, 4,200);
        processTest(i, results);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);  //
        System.out.println(duration);
        return duration;
    }

    public long test2(int i) {
        long startTime = System.nanoTime();
        System.out.println("----\nBinary representation" + i + ":");
        List<Integer> values = Arrays.asList(4, 2, 2, 1);
        List<Integer> weights = Arrays.asList(10, 3, 5, 2);
        List<Map<Integer, Float>> results = this.objectiveFunction(weights, values, 15, 4,200);
        processTest(i, results);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //
        System.out.println(duration);
        return duration;
    }

    public long test3(int i) {
        long startTime = System.nanoTime();
        System.out.println("----\nBinary representation" + i + ":");
        List<Integer> values = Arrays.asList(4, 2, 2, 1, 3, 7);
        List<Integer> weights = Arrays.asList(10, 3, 5, 2, 4, 1);
        List<Map<Integer, Float>> results = this.objectiveFunction(weights, values, 25, 4,200);
        processTest(i, results);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //
        System.out.println(duration);
        return duration;
    }
    public long test4(int i) {
        long startTime = System.nanoTime();
        List<Integer> values = Arrays.asList(4, 2, 2, 1, 10);
        List<Integer> weights = Arrays.asList(1, 2, 3, 10, 4);
        List<Map<Integer, Float>> results = this.objectiveFunction(weights, values, 105, 3,200);
        processTest(i, results);
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);  //
        System.out.println(duration);
        return duration;
    }

    public long test5(int i) {
        long startTime = System.nanoTime();
        System.out.println("----\nBinary representation" + i + ":");
        List<Integer> values = Arrays.asList(4, 2, 2, 1);
        List<Integer> weights = Arrays.asList(10, 3, 5, 2);
        List<Map<Integer, Float>> results = this.objectiveFunction(weights, values, 15, 3,200);
        processTest(i, results);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //
        System.out.println(duration);
        return duration;
    }

    public long test6(int i) {
        long startTime = System.nanoTime();
        System.out.println("----\nBinary representation" + i + ":");
        List<Integer> values = Arrays.asList(4, 2, 2, 1, 3, 7);
        List<Integer> weights = Arrays.asList(10, 3, 5, 2, 4, 1);
        List<Map<Integer, Float>> results = this.objectiveFunction(weights, values, 25, 3,200);
        processTest(i, results);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);  //
        System.out.println(duration);
        return duration;
    }
    public void test() {
        long res = test1(1);
        long res1 =test2(2);
        long res2 = test3(3);
        long res3 = test1(4);
        long res4 =test2(5);
        long res5 = test3(6);
        Map<Long, Integer> duration = new TreeMap<>();
        duration.put(res, 4);
        duration.put(res1, 4);
        duration.put(res2, 4);
        duration.put(res3, 3);
        duration.put(res4, 3);
        duration.put(res5, 3);
        makePlot1(duration, "duration", "k", "duration");

    }
}