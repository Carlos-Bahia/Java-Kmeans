package br.edu.ufape.kmeans.mains;

import br.edu.ufape.kmeans.Cluster;
import br.edu.ufape.kmeans.DataPoint;
import br.edu.ufape.kmeans.FileWriter;
import br.edu.ufape.kmeans.algorithm.Kmeans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainIris {

    public static void main(String[] args) {

        List<DataPoint> dataPoints = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(MainIris.class.getResourceAsStream("/iris.data"))))){

            String line;
            Integer idDataPoint = 1;
            while ((line = br.readLine()) != null){
                String[] values = line.split(",");
                double[] coordinatesDP = new double[values.length - 1];
                for (int i = 0; i < values.length-1; i++){
                    coordinatesDP[i] = Double.parseDouble(values[i]);
                }
                dataPoints.add(new DataPoint(idDataPoint, coordinatesDP));
                idDataPoint++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        //K para Iris
        int k = 3;
        Kmeans kmeans = new Kmeans(k, dataPoints);
        kmeans.initialize();
        kmeans.process();

        List<Cluster> clusters = kmeans.getClusters();
        FileWriter.writeClustersToFile("Iris-Kmeans-Manhattan.txt", clusters, 1, dataPoints);

    }
}