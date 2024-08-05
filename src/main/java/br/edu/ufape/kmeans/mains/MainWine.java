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

public class MainWine {

    public static void main(String[] args) {

        List<DataPoint> dataPoints = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(MainWine.class.getResourceAsStream("/wine.data"))))){

            String line;
            Integer idDataPoint = 1;
            while((line = br.readLine()) != null) {
                String[] values = line.split(",");
                double[] coordinatesDP = new double[values.length - 1];
                for(int i = 1; i < values.length; i++){
                    coordinatesDP[i-1] = Double.parseDouble(values[i]);
                }
                dataPoints.add(new DataPoint(idDataPoint, coordinatesDP));
                idDataPoint++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //K para Rice
        int k = 3;
        Kmeans kmeans = new Kmeans(k, dataPoints);
        kmeans.initialize();
        kmeans.process();

//        Esperado
//        class 1 59
//        class 2 71
//        class 3 48

        List<Cluster> clusters = kmeans.getClusters();
        FileWriter.writeClustersToFile("wine-Kmeans-Chebyshev.txt", clusters, 4, dataPoints);
    }
}
