package br.edu.ufape.kmeans.indexes;

import br.edu.ufape.kmeans.Cluster;
import br.edu.ufape.kmeans.DataPoint;

import java.util.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class IndexUtils {

    public static double calculateEuclideanDistance(double[] coordinatesDataPoint, double[] coordinatesCluster) {
        double sum = 0.0;

        for (int i = 0; i < coordinatesDataPoint.length; i++) {
            sum += pow((coordinatesDataPoint[i] - coordinatesCluster[i]), 2);
        }

        return sqrt(sum);
    }

    public static Map<Integer, Cluster> findExpectedMatrix(int dataBase, List<Cluster> clusters) {
        Map<Integer, Cluster> expectedMap = new HashMap<>();
        Integer numClasses = 0;
        Integer interval = 0;

        switch (dataBase) {
            case 1: // Iris
                numClasses = 3;
                interval = 50;
                break;

            case 2: // Mfeat
                numClasses = 10;
                interval = 200;
                break;

            case 3: // Rice
                numClasses = 2;
                break;

            case 4: //Wine
                numClasses = 3;
                break;
        }

        List<Boolean> usedCluster = new ArrayList<>(Collections.nCopies(numClasses, false));

        if(dataBase == 1 || dataBase == 2){

            for(int classe = 0;  classe < numClasses; classe++){
                int begin = classe * interval;
                int end = (classe + 1) * interval -1;
                int maxPoints = -1;
                Cluster bestCluster = null;
                int bestIndex = -1;

                for(int i = 0; i < clusters.size(); i++){
                    if(!usedCluster.get(i)){
                        int counter = 0;
                        for(DataPoint dp : clusters.get(i).getDataPoints()){
                            if(dp.getId() >= begin && dp.getId() <= end){
                                counter++;
                            }
                        }
                        if(counter > maxPoints){
                            maxPoints = counter;
                            bestCluster = clusters.get(i);
                            bestIndex = i;
                        }
                    }
                }

                if(bestCluster != null && bestIndex != -1) {
                    usedCluster.set(bestIndex, true);
                    for(int j = begin; j <= end; j++){
                        expectedMap.put(j, bestCluster);
                    }
                }

            }
        }  else if (dataBase == 3) {
            // Para o database 3 com 2 clusters de 1630 e 2180 elementos

            interval = 1630;
            int[] clusterSizes = {1630, 2180};
            int begin = 1;

            List<Cluster> availableClusters = new ArrayList<>(clusters);

            int[] dataPointCounts = new int[availableClusters.size()];

            for(int k = 0 ; k < numClasses; k++) {
                for (int clusterIndex = 0; clusterIndex < availableClusters.size(); clusterIndex++) {
                    Cluster currentCluster = availableClusters.get(clusterIndex);
                    List<DataPoint> dataPoints = currentCluster.getDataPoints();

                    int count = 0;

                    for (DataPoint dp : dataPoints) {
                        if (dp.getId() >= begin && dp.getId() <= begin + interval - 1) {
                            count++;
                        }
                    }

                    dataPointCounts[clusterIndex] = count;
                }

                int bestClusterIndex = 0;
                int maxPoints = dataPointCounts[0];

                for (int i = 1; i < dataPointCounts.length; i++) {
                    if (dataPointCounts[i] > maxPoints) {
                        maxPoints = dataPointCounts[i];
                        bestClusterIndex = i;
                    }
                }

                Cluster bestCluster = availableClusters.get(bestClusterIndex);
                for (int j = begin; j <= begin + interval - 1; j++) {
                    expectedMap.put(j, bestCluster);
                }

                // Opcional: Remover o cluster escolhido da lista de clusters disponíveis
                availableClusters.remove(bestClusterIndex);
                begin = interval;
            }
        } else if (dataBase == 4) {
            // Para o database 4 com 3 clusters de 59, 71 e 48 elementos

            List<Integer> clusterSizes = new LinkedList<>(List.of(59, 71, 48));
            interval = clusterSizes.getFirst();
            int begin = 1;

            List<Cluster> availableClusters = new ArrayList<>(clusters);

            while (!clusterSizes.isEmpty()) {
                int[] dataPointCounts = new int[availableClusters.size()];

                for (int clusterIndex = 0; clusterIndex < availableClusters.size(); clusterIndex++) {
                    Cluster currentCluster = availableClusters.get(clusterIndex);
                    List<DataPoint> dataPoints = currentCluster.getDataPoints();

                    int count = 0;
                    for (DataPoint dp : dataPoints) {
                        if (dp.getId() >= begin && dp.getId() <= begin + interval) {
                            count++;
                        }
                    }

                    dataPointCounts[clusterIndex] = count;
                }

                int bestClusterIndex = 0;
                int maxPoints = dataPointCounts[0];

                for (int i = 1; i < dataPointCounts.length; i++) {
                    if (dataPointCounts[i] > maxPoints) {
                        maxPoints = dataPointCounts[i];
                        bestClusterIndex = i;
                    }
                }

                Cluster bestCluster = availableClusters.get(bestClusterIndex);
                for (int j = begin; j <= begin + interval - 1; j++) {
                    expectedMap.put(j, bestCluster);
                }

                // Atualizar o início para o próximo intervalo
                begin += interval;

                // Atualizar o intervalo para o próximo tamanho de cluster, se disponível
                if (!clusterSizes.isEmpty()) {
                    clusterSizes.removeFirst();
                    interval = clusterSizes.isEmpty() ? 0 : clusterSizes.getFirst();
                }
            }
        }

        return expectedMap;
    }

    public static Map<Integer, Cluster> findRealMatrix(int dataBase, List<Cluster> clusters){
        Map<Integer, Cluster> map = new HashMap<>();

        for (Cluster cluster : clusters){
            for(DataPoint dp : cluster.getDataPoints()){
                map.put(dp.getId(), cluster);
            }
        }

        return map;
    }
}
