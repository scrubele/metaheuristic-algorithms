package main.java.com.scrubele.representations;

import java.util.List;


interface Metaheuristic {
    List<Number> getEncoding();

    int objectiveFunction();
}