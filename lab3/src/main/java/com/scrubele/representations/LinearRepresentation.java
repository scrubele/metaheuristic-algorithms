package main.java.com.scrubele.representations;

import java.util.ArrayList;
import java.util.List;

public class LinearRepresentation implements Metaheuristic {

    List<Number> encoding;

    @Override
    public List<Number> getEncoding() {
        return new ArrayList<>(this.encoding);
    }

    @Override
    public int objectiveFunction() {
        return 0;
    }

    public void printEncoding() {
        List<Number> list = this.getEncoding();
        System.out.println("Encoding: " + list);
    }
}