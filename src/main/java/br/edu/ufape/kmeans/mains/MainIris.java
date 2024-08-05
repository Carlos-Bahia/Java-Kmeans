package br.edu.ufape.kmeans.mains;

import br.edu.ufape.kmeans.Cluster;
import br.edu.ufape.kmeans.DataPoint;
import br.edu.ufape.kmeans.FileWriter;
import br.edu.ufape.kmeans.algorithm.FireflyKmeans;

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

        int numFireflies = 50;
        int maxInterations = 100;
        double alpha = 0.2;
        double beta = 1.0;
        double gamma = 1.0;

        FireflyKmeans fireflyKmeans = new FireflyKmeans(k, dataPoints, numFireflies, maxInterations, alpha, beta, gamma);
        fireflyKmeans.initialize();
        fireflyKmeans.process();


        List<Cluster> clusters = fireflyKmeans.getClusters();
        FileWriter.writeClustersToFile("Firefly-iris-Kmeans.txt", clusters, 1, dataPoints);

    }
}