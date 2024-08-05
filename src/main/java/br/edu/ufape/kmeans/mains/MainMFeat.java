package br.edu.ufape.kmeans.mains;

import br.edu.ufape.kmeans.Cluster;
import br.edu.ufape.kmeans.DataPoint;
import br.edu.ufape.kmeans.FileWriter;
import br.edu.ufape.kmeans.algorithm.Kmeans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainMFeat {
    public static void main(String[] args) {
        List<DataPoint> dataPoints = new ArrayList<>();

        // Lista de arquivos Mfeat
        String[] filenames = {
                "/mfeat-fac", "/mfeat-fou", "/mfeat-kar",
                "/mfeat-zer", "/mfeat-mor", "/mfeat-pix"
        };

        int numFeatures = 0;


        // Primeiro, calcule o número total de características
        for (String filename : filenames) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(MainMFeat.class.getResourceAsStream(filename))))) {
                String line;
                if ((line = br.readLine()) != null) {
                    numFeatures += line.trim().split("\\s+").length;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        List<double[]> allCoordinates = new ArrayList<>();
        for(int i = 0; i < 2000; i++){
            double[] data = new double[numFeatures];
            allCoordinates.add(data);
        }

        int lastFeature = 0;
        for (String filename : filenames) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(
                    Objects.requireNonNull(MainMFeat.class.getResourceAsStream(filename))))) {
                String line;
                int dataPointIndice = 0;
                while ((line = br.readLine()) != null) {
                    String[] values = line.trim().split("\\s+");
                    double[] coordinates = allCoordinates.get(dataPointIndice);
                    for (int i = 0; i < values.length; i++) {
                        coordinates[i+lastFeature] = Double.parseDouble(values[i]);
                    }
                    dataPointIndice++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                switch (filename) {
                    case "/mfeat-fac":
                        lastFeature += 76;
                        break;

                    case "/mfeat-fou":
                        lastFeature += 216;
                        break;

                    case "/mfeat-kar":
                        lastFeature += 64;
                        break;

                    case "/mfeat-pix":
                        lastFeature += 240;
                        break;

                    case "/mfeat-zer":
                        lastFeature += 47;
                        break;

                    case "/mfeat-mor":
                        lastFeature += 6;
                        break;
                }
            }
        }

        // Crie os DataPoints a partir dos dados combinados
        Integer idDataPoint = 1;
        for (double[] coordinates : allCoordinates) {
            dataPoints.add(new DataPoint(idDataPoint, coordinates));
            idDataPoint++;
        }

        int k = 10; // Número de clusters, ajuste conforme necessário
        Kmeans kmeans = new Kmeans(k, dataPoints);
        kmeans.initialize();
        kmeans.process();

        List<Cluster> clusters = kmeans.getClusters();
        FileWriter.writeClustersToFile("mfeat-Kmeans-Chebyshev.txt", clusters, 2, dataPoints);
    }
}
