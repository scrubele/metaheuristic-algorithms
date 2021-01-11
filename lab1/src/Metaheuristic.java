import java.util.List;


interface Metaheuristic {
    List<Number> getEncoding();
    int objectiveFunction();
}