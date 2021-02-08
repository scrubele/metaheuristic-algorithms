import java.util.ArrayList;
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


    public int objectiveFunction(KnapsackItem[] items, int weightLimit) {
        /*
        The Knapsack algorithm.
         */
        int itemsNumber = items.length;
        int[][] matrix = new int[itemsNumber + 1][weightLimit + 1];
        matrix = formMatrix(items, matrix, weightLimit);
        chooseItems(items, matrix, weightLimit);
        formResult(items);
        return matrix[itemsNumber][weightLimit];
    }

    public int[][] formMatrix(KnapsackItem[] items, int[][] matrix, int weightLimit) {
        /*
            Forming dynamic matrix;
         */
        int itemsNumber = items.length;
        int item = 0;
        int weight = 0;

        for (item = 0; item <= itemsNumber; item++) {
            for (weight = 0; weight <= weightLimit; weight++) {
                if (item == 0 || weight == 0)
                    matrix[item][weight] = 0;
                else if (items[item - 1].weight <= weight)
                    matrix[item][weight] = Math.max(items[item - 1].value +
                            matrix[item - 1][weight - items[item - 1].weight], matrix[item - 1][weight]);
                else
                    matrix[item][weight] = matrix[item - 1][weight];
            }
        }
        return matrix;
    }

    public void chooseItems(KnapsackItem[] items, int[][] matrix, int weightLimit) {
        /*
        The choosing items to Knapsack.
         */
        int itemsNumber = items.length;
        int result = matrix[itemsNumber][weightLimit];
        int currentWeight = weightLimit;
        for (int i = itemsNumber; i > 0 && result > 0; i--) {
            if (result != matrix[i - 1][currentWeight]) {
                items[i - 1].inKnapsack = 1;
                result -= items[i - 1].value;
                currentWeight -= items[i - 1].weight;
            }
        }
    }

    public void formResult(KnapsackItem[] items) {
        /*
            Forming result encoding list.
         */
        for (KnapsackItem knapsackItem : items) {
            this.encoding.add(knapsackItem.inKnapsack);
        }
    }

    public void test() {
        System.out.println("----\nBinary representation:");
        KnapsackItem[] items = {
                new KnapsackItem("laptop", 4, 20),
                new KnapsackItem("pen", 2, 1),
                new KnapsackItem("towel", 2, 1),
                new KnapsackItem("pants", 1, 2),
                new KnapsackItem("pan", 10, 4)
        };
        int result = this.objectiveFunction(items, 15);
        for (KnapsackItem item : items) {
            System.out.println(item);
        }
        System.out.println("Binary representation result: " + result);
        this.printEncoding();
    }
}

class KnapsackItem {
    public String name;
    public int value;
    public int weight;
    protected int inKnapsack = 0;

    public KnapsackItem(String name, int value, int weight) {
        this.name = name;
        this.value = value;
        this.weight = weight;
    }

    public String toString() {
        return name + " [value = " + value + ", weight = " + weight + ", inKnapsack = " + inKnapsack + "]";
    }
}
