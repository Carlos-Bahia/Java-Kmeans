package br.edu.ufape.kmeans.indexes;

import br.edu.ufape.kmeans.Cluster;
import br.edu.ufape.kmeans.DataPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static br.edu.ufape.kmeans.indexes.IndexUtils.*;


public class AdjustedRandIndex {

    private static List<List<Integer>> findContingencyMatrix(Map<Integer, Cluster> expectedMap, Map<Integer, Cluster> realMap, List<Cluster> clusters) {
        int numClusters = clusters.size();

        // Inicializar a matriz de contingência com zeros
        List<List<Integer>> contingencyMatrix = new ArrayList<>();
        for (int i = 0; i < numClusters; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < numClusters; j++) {
                row.add(0);
            }
            contingencyMatrix.add(row);
        }

        // Preencher a matriz de contingência
        for (Map.Entry<Integer, Cluster> entry : expectedMap.entrySet()) {
            int index = entry.getKey();
            Cluster expectedCluster = entry.getValue();
            Cluster realCluster = realMap.get(index);

            // Verifica se o realCluster não é nulo
            if (realCluster != null) {
                int realIndex = clusters.indexOf(realCluster);
                int expectedIndex = clusters.indexOf(expectedCluster);

                // Verifica se ambos índices são válidos
                if (realIndex >= 0 && expectedIndex >= 0) {
                    int currentCount = contingencyMatrix.get(realIndex).get(expectedIndex);
                    contingencyMatrix.get(realIndex).set(expectedIndex, currentCount + 1);
                }
            }
        }

        return contingencyMatrix;
    }

    public static double adjustedRandIndexEvaluation(List<Cluster> clusters, int dataBase, List<DataPoint> dataPoints) {
        Map<Integer, Cluster> expectedMap = findExpectedMatrix(dataBase, clusters);
        Map<Integer, Cluster> realMap = findRealMatrix(dataBase, clusters);

        List<List<Integer>> contigencyMatrix = findContingencyMatrix(expectedMap, realMap, clusters);

        int numDataPoints = dataPoints.size();
        int numClasses = clusters.size();

        int totalPairs = (numDataPoints * (numDataPoints-1)) / 2;

        int sumRealClusters = 0;
        for(int i = 0; i < numClasses; i ++) {
            int sumClasse = 0;
            for(int j = 0; j < numClasses; j++){
                sumClasse += contigencyMatrix.get(i).get(j);
            }
            sumRealClusters += (sumClasse * (sumClasse - 1)) / 2;
        }

        int sumExpectedClusters = 0;
        for(int j = 0; j < numClasses; j++){
            int sumClasse = 0;
            for (int i = 0; i < numClasses; i++){
                sumClasse += contigencyMatrix.get(i).get(j);
            }
            sumExpectedClusters += (sumClasse * (sumClasse - 1)) / 2;
        }

        int observedIndex = 0;
        for(int i = 0; i < numClasses; i++){
            for(int j = 0; j < numClasses; j++){
                if(contigencyMatrix.get(i).get(j) > 0){
                    observedIndex += (contigencyMatrix.get(i).get(j) * (contigencyMatrix.get(i).get(j) - 1)) /2;
                }
            }
        }

        double expectedIndex = ((double) sumRealClusters * (double) sumExpectedClusters) / (double) totalPairs;
        double maxIndex = 0.5 * (sumRealClusters + sumExpectedClusters);

        return (observedIndex - expectedIndex) / (maxIndex - expectedIndex);
    }
}
