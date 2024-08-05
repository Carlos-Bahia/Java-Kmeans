package br.edu.ufape.kmeans.indexes;

import br.edu.ufape.kmeans.Cluster;

import java.util.*;

import static br.edu.ufape.kmeans.indexes.IndexUtils.*;

public class FMeasure {

    public static double fmeasureEvaluation(List<Cluster> clusters, int dataBase) {
        Map<Integer, Cluster> expectedMap = findExpectedMatrix(dataBase, clusters);
        Map<Integer, Cluster> realMap = findRealMatrix(dataBase, clusters);

        int truePositive = 0;
        int falsePositive = 0;
        int falseNegative = 0;

        for(Map.Entry<Integer, Cluster> entry : realMap.entrySet()){
            int id = entry.getKey();
            Cluster realCluster = entry.getValue();
            Cluster expectedCluster = expectedMap.get(id);

            if(realCluster.equals(expectedCluster)){
                truePositive++;
            } else {
                falsePositive++;
                if(expectedCluster != null) {
                    falseNegative++;
                }
            }
        }

        double precision = (truePositive + 0.0) / (truePositive + falsePositive);
        double recall = (truePositive + 0.0) / (truePositive + falseNegative);

        return 2 * (precision * recall) / (precision + recall);
    }

}
