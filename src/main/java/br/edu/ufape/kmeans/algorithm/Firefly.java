package br.edu.ufape.kmeans.algorithm;

import br.edu.ufape.kmeans.DataPoint;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class Firefly {
    private List<DataPoint> centroids;
    private double brightness;

}
