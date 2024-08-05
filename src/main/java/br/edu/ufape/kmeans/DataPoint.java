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

    public double distance(DataPoint other) {
        double sum = 0;
        for (int i = 0; i < coordinates.length; i++) {
            sum += Math.pow(coordinates[i] - other.coordinates[i], 2);
        }
        return Math.sqrt(sum);
    }
}
