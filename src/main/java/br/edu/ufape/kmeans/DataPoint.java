package br.edu.ufape.kmeans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataPoint {

    private Integer id;
    private double[] coordinates;
    private Cluster cluster;

    public DataPoint(Integer id, double[] coordinates) {
        this.id = id;
        this.coordinates = coordinates;
    }

    public DataPoint(double[] coordinates) {
        this.coordinates = coordinates;
    }

    //Distância de Chebyshev
    public double distance(DataPoint other) {
        double maxDistance = 0;
        for (int i = 0; i < coordinates.length; i++) {
            double dist = Math.abs(coordinates[i] - other.coordinates[i]);
            if (dist > maxDistance) {
                maxDistance = dist;
            }
        }
        return maxDistance;
    }
}
