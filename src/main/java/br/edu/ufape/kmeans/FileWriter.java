package br.edu.ufape.kmeans;

import br.edu.ufape.kmeans.indexes.AdjustedRandIndex;
import br.edu.ufape.kmeans.indexes.CalinskiHarabasz;
import br.edu.ufape.kmeans.indexes.DaviesBouldin;
import br.edu.ufape.kmeans.indexes.FMeasure;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileWriter {

    public static void writeClustersToFile(String filename, List<Cluster> clusters, int dataBase, List<DataPoint> dataPoints) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename))) {

            writer.write("Informações gerais sobre o agrupamento \n\n");
            writer.write(String.format("Fmeasure : %f\n", FMeasure.fmeasureEvaluation(clusters, dataBase)));
            writer.write(String.format("Adjusted Rand Index : %f\n", AdjustedRandIndex.adjustedRandIndexEvaluation(clusters, dataBase, dataPoints)));
            writer.write(String.format("Davies-Bouldin: %f\n", DaviesBouldin.daviesBouldinEvaluation(clusters)));
            writer.write(String.format("Calinski-Harabasz: %f\n\n", CalinskiHarabasz.calinskiHarabaszEvaluation(clusters, dataPoints)));
            Integer counter = 1;

            for(Cluster cluster : clusters) {
                writer.write(String.format("Cluster %d:\n", counter));
                writer.write((String.format("Size: %d\n", cluster.getDataPoints().size())));
            }

            writer.write("\n\nInformações detalhadas dos clusters: \n\n");
            counter = 1;
            for (Cluster cluster : clusters) {
                writer.write(String.format("Cluster %d:\n", counter));
                writer.write((String.format("Size: %d\n", cluster.getDataPoints().size())));
                for (DataPoint dp : cluster.getDataPoints()) {
                    writer.write(dp.getId() + " ");
                    writer.write(Arrays.toString(dp.getCoordinates()) + "\n");
                }
                writer.write("\n");
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
