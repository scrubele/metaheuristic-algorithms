package main.java.com.scrubele.utilities;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

public class Utility {

    private static final Random rng = new Random();

    public static int[][] generateRandomMatrix(int height, int width, int maxRand) {
        int[][] data = new int[height][width];
        int n, m;
        for (n = 0; height > n; n++) {
            for (m = 0; width > m; m++) {
                data[n][m] = rng.nextInt(maxRand);
            }
        }
        return data;
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

    public static Map<String, Float> convertMapKeyToString(Map<List<Integer>, Float> candidateSolutionValues) {
        Map<String, Float> candidateSolutionIntValues = new LinkedHashMap<>();
        for (Map.Entry<List<Integer>, Float> entry : candidateSolutionValues.entrySet()) {
            List<Integer> key = entry.getKey();
            float value = entry.getValue();
            String integerKey = key.stream().map(Object::toString).collect(Collectors.joining("_"));
            candidateSolutionIntValues.put(integerKey, value);
        }
        return candidateSolutionIntValues;
    }

    public static Map<Integer, Float> convertMapKeyToInteger(Map<List<Integer>, Float> candidateSolutionValues) {
        Map<Integer, Float> candidateSolutionIntValues = new LinkedHashMap<>();
        for (Map.Entry<List<Integer>, Float> entry : candidateSolutionValues.entrySet()) {
            List<Integer> key = entry.getKey();
            float value = entry.getValue();
            int integerKey = Integer.parseInt(key.stream().map(Object::toString)
                    .collect(Collectors.joining("")));
            candidateSolutionIntValues.put(integerKey, value);
        }
        return candidateSolutionIntValues;
    }


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

    public static void writeHashMapToCsv(Map<String, Float> linkedHashMap, String name) {
        String eol = System.getProperty("line.separator");
        List<String> reverseOrderedKeys = new ArrayList<>(linkedHashMap.keySet());
//        Collections.reverse(reverseOrderedKeys);
        try (
                Writer writer = new FileWriter(name, true)) {
            for (String key : reverseOrderedKeys) {
                Float value = linkedHashMap.get(key);
                writer.append(key)
                        .append(',')
                        .append(value.toString())
                        .append(eol);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }

    }

    public static void writeHashMapToCsv(Map<String, Float> linkedHashMap, Map<String, Float> descentMap,
                                         String name) {
        String eol = System.getProperty("line.separator");
        List<String> reverseOrderedKeys = new ArrayList<>(linkedHashMap.keySet());
//        Collections.reverse(reverseOrderedKeys);
        String descentKey = descentMap.keySet().stream().findFirst().get();
//        Float valut = descentMap
        try (
                Writer writer = new FileWriter(name, true)) {
            for (String key : reverseOrderedKeys) {
                Float value = linkedHashMap.get(key);
                writer.append(key)
                        .append(',')
                        .append(value.toString())
                        .append(',');
                if (key.equals(descentKey))
                    writer.append(value.toString());
                else
                    writer.append(" ");
                writer.append(eol);
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
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
