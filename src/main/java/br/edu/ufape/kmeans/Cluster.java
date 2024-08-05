package br.edu.ufape.kmeans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Cluster {
    private DataPoint centroid;
    private List<DataPoint> dataPoints;

    public Cluster(DataPoint centroid) {
        this.centroid = centroid;
        this.dataPoints = new ArrayList<>();
    }

    public void calculateCentroid() {
        if (dataPoints.isEmpty())
            return;

        int numCoordinates = this.centroid.getCoordinates().length;
        double[] newCoordinates = new double[numCoordinates];

        for (DataPoint dp : dataPoints) {
            for (int i = 0; i < numCoordinates; i++) {
                newCoordinates[i] += dp.getCoordinates()[i];
            }
        }

        for (int i = 0; i < numCoordinates; i++) {
            newCoordinates[i] /= dataPoints.size();
        }

        centroid = new DataPoint(newCoordinates);
    }

    public void addDataPoint(DataPoint dataPoint) {
        dataPoints.add(dataPoint);
    }

    public void clearDataPoints() {
        dataPoints.clear();
    }
}
