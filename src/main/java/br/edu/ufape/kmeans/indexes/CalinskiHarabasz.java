package br.edu.ufape.kmeans.indexes;

import br.edu.ufape.kmeans.Cluster;
import br.edu.ufape.kmeans.DataPoint;

import java.util.List;

import static br.edu.ufape.kmeans.indexes.IndexUtils.calculateEuclideanDistance;
import static java.lang.Math.pow;

public class CalinskiHarabasz {

    public static double calinskiHarabaszEvaluation(List<Cluster> clusters, List<DataPoint> dataPoints) {

        double[] globalCentroid = calculateGlobalCentroid(dataPoints);

        double B = 0.0;
        double W = 0.0;

        for (Cluster cluster : clusters) {
            double[] clusterCentroid = cluster.getCentroid().getCoordinates();
            double distanceToGlobal = calculateEuclideanDistance(clusterCentroid, globalCentroid);
            B += pow(distanceToGlobal, 2) * cluster.getDataPoints().size();

            List<DataPoint> clusterDataPoints = cluster.getDataPoints();
            for (DataPoint dp : clusterDataPoints) {
                double[] dpCoordinates = dp.getCoordinates();
                double distanceToCentroid = calculateEuclideanDistance(dpCoordinates, clusterCentroid);
                W += pow(distanceToCentroid, 2);
            }
        }

        int numClusters = clusters.size();
        int numDataPoints = dataPoints.size();

        double betweenClusterVariance = B / (numClusters - 1);
        double withinClusterVariance = W / (numDataPoints - numClusters);

        return betweenClusterVariance / withinClusterVariance;
    }

    private static double[] calculateGlobalCentroid(List<DataPoint> dataPoints) {
        if (dataPoints.isEmpty()) {
            throw new IllegalArgumentException("A lista de pontos de dados não pode estar vazia.");
        }

        int dimensions = dataPoints.getFirst().getCoordinates().length;
        double[] globalCentroid = new double[dimensions];

        for (DataPoint dp : dataPoints) {
            double[] coordinates = dp.getCoordinates();
            for (int i = 0; i < dimensions; i++) {
                globalCentroid[i] += coordinates[i];
            }
        }

        int numberOfDataPoints = dataPoints.size();
        for (int i = 0; i < dimensions; i++) {
            globalCentroid[i] /= numberOfDataPoints;
        }

        return globalCentroid;
    }
}
