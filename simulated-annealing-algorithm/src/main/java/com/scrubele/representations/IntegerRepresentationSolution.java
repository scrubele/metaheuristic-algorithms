package main.java.com.scrubele.representations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class IntegerRepresentationSolution {

    public List<Integer> encoding;
    public Float value;
    public Double probability;
    public Double loss;
    public Double temperature = (double) 0;


    public IntegerRepresentationSolution() {
        this.encoding = new ArrayList<>();
        this.value = Float.NaN;
        this.probability = (double) 0;
        this.loss = (double) 0;
    }

    public IntegerRepresentationSolution(List<Integer> encoding, Float value) {
        this.encoding = encoding;
        this.value = value;
        this.probability = (double) 0;
        this.loss = (double) 0;
    }

    public List<Number> getEncoding() {
        return new ArrayList<>(this.encoding);
    }

    public void initializeSolution(int solutionSetSize) {
        this.encoding = IntStream.rangeClosed(0, solutionSetSize - 1)
                .boxed().collect(Collectors.toList());
        Collections.shuffle(this.encoding);
    }


    @Override
    public String toString() {
        return "Solution{" +
                "encoding=" + encoding +
                ", value=" + value +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerRepresentationSolution solution = (IntegerRepresentationSolution) o;
        return Objects.equals(value, solution.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


    public int getSize() {
        return this.encoding.size();
    }

    public abstract IntegerRepresentationSolution generateRandomNeighbour();

    public abstract List<Integer> generateNeighbour(int itemToFlip);

    public String getCommaSeparatedValues() {
        String encoding = this.encoding.stream().map(Object::toString).collect(Collectors.joining("_"));
        return encoding + "," + this.value + "," + this.loss;
    }

}