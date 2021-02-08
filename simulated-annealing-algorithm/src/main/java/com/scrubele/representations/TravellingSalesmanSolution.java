package main.java.com.scrubele.representations;

import main.java.com.scrubele.utilities.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class TravellingSalesmanSolution extends IntegerRepresentationSolution {

    ArrayList<ArrayList<Float>> weights;

    public TravellingSalesmanSolution(ArrayList<ArrayList<Float>> weights) {
        this.encoding = new ArrayList<>();
        this.weights = weights;
        initializeSolution();
        getConvergence(this.weights);
        System.out.println(this);
    }

    public TravellingSalesmanSolution(List<Integer> encoding, ArrayList<ArrayList<Float>> weights) {
        this.encoding = encoding;
        this.weights = weights;
        getConvergence(this.weights);
    }

    public void initializeSolution() {
        int solutionSetSize = weights.size();
        this.encoding = IntStream.rangeClosed(0, solutionSetSize - 1)
                .boxed().collect(Collectors.toList());
        Collections.shuffle(this.encoding);
    }

    public float getConvergence(ArrayList<ArrayList<Float>> weights) {
        ArrayList<ArrayList<Float>> localWeights = weights;
        float solutionWeight = 0;
        List<Integer> currentSolution = new ArrayList<>(this.encoding);
        currentSolution.add(0, this.encoding.get(this.encoding.size() - 1));
        for (int previousIndex = 0, currentIndex = 1; currentIndex < currentSolution.size(); previousIndex++,
                currentIndex++) {
            int cityFrom = currentSolution.get(previousIndex);
            int cityTo = currentSolution.get(currentIndex);
            float weight = localWeights.get(cityFrom).get(cityTo);
            solutionWeight += weight;
        }
        this.value = solutionWeight;
        return solutionWeight;
    }

    public List<Integer> generateNeighbour(int itemToFlip) {
        List<Integer> currentSolution = new ArrayList<>(this.encoding);
        Collections.shuffle(currentSolution);
//        int newItemValue = Utility.flipValue(currentSolution.get(itemToFlip));
//        currentSolution.set(itemToFlip, newItemValue);
        return currentSolution;
    }

    public List<Integer> generateSolution(int startItem, int lastItem) {
        List<Integer> currentSolution = new ArrayList<>(this.encoding);
        if (lastItem - startItem > 0) {
            List<Integer> newList = new ArrayList<>();
            List<Integer> beginning = new ArrayList<>();
            List<Integer> ending = new ArrayList<>();
            List<Integer> sublist = currentSolution.subList(startItem, lastItem);
            Collections.reverse(sublist);
            if (startItem > 0)
                beginning = currentSolution.subList(0, startItem);
            if (lastItem < currentSolution.size())
                ending = currentSolution.subList(lastItem, currentSolution.size());
            newList.addAll(beginning);
            newList.addAll(sublist);
            newList.addAll(ending);
            return newList;
        }
        return currentSolution;

    }

    public TravellingSalesmanSolution generateRandomNeighbour() {
        int itemToFlip = Math.round((float) Math.random() * (weights.size() - 1));
        return new TravellingSalesmanSolution(generateNeighbour(itemToFlip), this.weights);
    }

    public int getSize() {
        return this.encoding.size();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
