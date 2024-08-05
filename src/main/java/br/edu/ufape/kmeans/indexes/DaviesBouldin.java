package br.edu.ufape.kmeans.indexes;

import br.edu.ufape.kmeans.Cluster;
import br.edu.ufape.kmeans.DataPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.edu.ufape.kmeans.indexes.IndexUtils.calculateEuclideanDistance;


public class DaviesBouldin {

    private static double calculateIntraClusterDistance(Cluster cluster) {
        double totalDistance = 0.0;
        List<DataPoint> dataPoints = cluster.getDataPoints();

        for (DataPoint dp : dataPoints) {
            totalDistance += calculateEuclideanDistance(dp.getCoordinates(), cluster.getCentroid().getCoordinates());
        }

        return totalDistance / dataPoints.size();  // Média das distâncias
    }

    public static double daviesBouldinEvaluation(List<Cluster> clusters) {
        int numClusters = clusters.size();
        List<Double> intraClusterDistances = new ArrayList<>(numClusters);
        Map<Pair<Integer, Integer>, Double> rValues = new HashMap<>();
        List<Double> maxRValues = new ArrayList<>(numClusters);

        // Calcula as distâncias intra-cluster para cada cluster
        for (Cluster cluster : clusters) {
            intraClusterDistances.add(calculateIntraClusterDistance(cluster));
        }

        // Calcula R(i, j) para cada par de clusters
        for (int i = 0; i < numClusters; i++) {
            for (int j = 0; j < numClusters; j++) {
                if (i != j) {
                    double interClusterDistance = calculateEuclideanDistance(
                            clusters.get(i).getCentroid().getCoordinates(),
                            clusters.get(j).getCentroid().getCoordinates()
                    );

                    double rValue = (intraClusterDistances.get(i) + intraClusterDistances.get(j)) / interClusterDistance;
                    rValues.put(new Pair<>(i, j), rValue);
                }
            }
        }

        // Encontra o valor máximo de R(i, j) para cada cluster i
        for (int i = 0; i < numClusters; i++) {
            double maxR = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < numClusters; j++) {
                if (i != j) {
                    Double rValue = rValues.get(new Pair<>(i, j));
                    if (rValue != null && rValue > maxR) {
                        maxR = rValue;
                    }
                }
            }
            maxRValues.add(maxR);
        }

        // Calcula a média dos valores máximos de R
        double daviesBouldinIndex = 0.0;
        for (double maxR : maxRValues) {
            daviesBouldinIndex += maxR;
        }

        return daviesBouldinIndex / numClusters;
    }

}

