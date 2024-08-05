package br.edu.ufape.kmeans.algorithm;

import br.edu.ufape.kmeans.Cluster;
import br.edu.ufape.kmeans.DataPoint;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Kmeans {
    private int k;
    private List<DataPoint> dataPoints;
    private List<Cluster> clusters;

    public Kmeans(int k, List<DataPoint> dataPoints) {
        this.k = k;
        this.dataPoints = dataPoints;
        this.clusters = new ArrayList<>();
    }

    public void initialize() {
        Random random = new Random();
        for (int i = 0; i < k; i++) {
            DataPoint centroid = dataPoints.get(random.nextInt(dataPoints.size()));
            clusters.add(new Cluster(centroid));
        }
    }

    public void assignClusters() {
        for (DataPoint dp : dataPoints) {
            Cluster nearestCluster = null;
            double minDistance = Double.MAX_VALUE;

            for (Cluster cluster : clusters) {
                double distance = dp.distance(cluster.getCentroid());
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestCluster = cluster;
                }
            }

            nearestCluster.addDataPoint(dp);
            dp.setCluster(nearestCluster);
        }
    }

    public void updateCentroids() {
        for (Cluster cluster : clusters) {
            cluster.calculateCentroid();
        }
    }

    public void clearClusters() {
        for (Cluster cluster : clusters) {
            cluster.clearDataPoints();
        }
    }

    public void process() {
        boolean convergence = false;
        while (!convergence) {
            clearClusters();
            assignClusters();

            List<DataPoint> oldCentroids = new ArrayList<>();
            for (Cluster cluster : clusters) {
                oldCentroids.add(cluster.getCentroid());
            }

            updateCentroids();

            convergence = true;
            for (int i = 0; i < clusters.size(); i++) {
                if (clusters.get(i).getCentroid().distance(oldCentroids.get(i)) != 0) {
                    convergence = false;
                    break;
                }
            }
        }
    }

}
