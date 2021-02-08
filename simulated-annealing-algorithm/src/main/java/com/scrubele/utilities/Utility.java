package main.java.com.scrubele.utilities;

import main.java.com.scrubele.representations.IntegerRepresentationSolution;
import main.java.com.scrubele.representations.MaximizationProblemSolution;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Utility {

    public static void clearTheFile(String name) {
        FileWriter fwOb = null;
        try {
            fwOb = new FileWriter(name, false);
            PrintWriter pwOb = new PrintWriter(fwOb, false);
            pwOb.flush();
            pwOb.close();
            fwOb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static ArrayList<ArrayList<Float>> readMatrixFromFile(String pathName) {
        ArrayList<ArrayList<Float>> readMatrix = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File("src/test/resources/" + pathName));
            while (input.hasNextLine()) {
                Scanner columnReader = new Scanner(input.nextLine());
                ArrayList<Float> column = new ArrayList<>();
                while (columnReader.hasNextFloat()) {
                    column.add(columnReader.nextFloat());
                }
                readMatrix.add(column);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return readMatrix;
    }

    public static void writeSolutionsToCSV(LinkedList<IntegerRepresentationSolution> linkedList,
                                           String name) {
        String eol = System.getProperty("line.separator");
        try (
                Writer writer = new FileWriter(name, true)) {
            for (IntegerRepresentationSolution maximizationProblemSolution : linkedList) {
                writer.append(maximizationProblemSolution.getCommaSeparatedValues());
                writer.append(eol);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }

    }

    public static void printMatrix(ArrayList<ArrayList<Float>> matrix) {
        for (ArrayList<Float> floats : matrix) {
            for (int j = 0; j < floats.size(); j++) {
                System.out.print(floats.get(j) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static List<Integer> convertFromIntegerToBinaryList(int initialValue) {
        List<Integer> initialSolution = new ArrayList<>();
        String initialValueStr = Integer.toBinaryString(initialValue);
        System.out.println(initialValue + " " + initialValueStr);
        for (int i = 0; i < initialValueStr.length(); i++)
            initialSolution.add(Character.getNumericValue(initialValueStr.charAt(i)));
        return initialSolution;
    }

    public static int convertFromBinaryListToInteger(List<Integer> binarySolution) {
        String stringSolution = binarySolution.stream().map(Object::toString)
                .collect(Collectors.joining(""));
        int solution = Integer.parseInt(stringSolution, 2);
        return solution;
    }


    public static int flipValue(int value) {
        return value ^ 1;
    }

    public void makePlot(Map<Integer, Integer> dataCollection, String plotName, String xAxisName, String yAxisName) {
        Plot plot = Plot.plot(null);
        Plot.Data data = Plot.data();
        for (Map.Entry<Integer, Integer> entry : dataCollection.entrySet()) {
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





}
