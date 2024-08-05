package br.edu.ufape.kmeans.algorithm;

import br.edu.ufape.kmeans.Cluster;
import br.edu.ufape.kmeans.DataPoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FireflyKmeans extends Kmeans{
    private int fireflys;
    private int maxInterations;
    private double alpha;
    private double beta;
    private double gamma;

    public FireflyKmeans(int k, List<DataPoint> dataPoints, int fireflys, int maxInterations, double alpha, double beta, double gamma) {
        super(k, dataPoints);
        this.fireflys = fireflys;
        this.maxInterations = maxInterations;
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
    }

    @Override
    public void initialize() {
        Firefly firefly = fireflyAlgorithm();

        this.getClusters().clear();
        for (DataPoint centroid : firefly.getCentroids()) {
            getClusters().add(new Cluster(centroid));
        }
    }

    public Firefly fireflyAlgorithm() {

        List<Firefly> fireflies = initializeFireflies();

        for(int t = 0; t < this.maxInterations; t++){
            for(int i = 0; i < this.fireflys; i++){
                for(int j = 0; j < this.fireflys; j++){
                    if(fireflies.get(j).getBrightness() < fireflies.get(i).getBrightness()) {
                        moveFirefly(fireflies.get(i), fireflies.get(j));
                    }
                }
            }
        }

        return findBestFirefly(fireflies);
    }

    private List<Firefly> initializeFireflies() {
        List<Firefly> fireflies = new ArrayList<>();
        Random random = new Random();

        for(int i = 0; i < this.fireflys; i++){
            List<DataPoint> centroids = new ArrayList<>();
            for(int j = 0; j < this.getK(); j++){
                centroids.add(this.getDataPoints().get(random.nextInt(this.getDataPoints().size())));
            }
            double brightness = calculateBrightness(centroids);
            fireflies.add(new Firefly(centroids, brightness));
        }

        return fireflies;
    }

    private double calculateBrightness(List<DataPoint> centroids) {
        double brightness = 0.0;

        for(DataPoint point : this.getDataPoints()){
            double minDistance = Double.MAX_VALUE;

            for (DataPoint dataPoint : centroids) {
                double distance = point.distance(dataPoint);
                if(distance < minDistance) {
                    minDistance = distance;
                }
            }
            brightness += minDistance;
        }
        return brightness;
    }

    private void moveFirefly(Firefly flyI, Firefly flyJ) {
        Random random = new Random();
        List<DataPoint> newCentroids = new ArrayList<>();

        for(int d = 0; d < flyI.getCentroids().size(); d++){

            DataPoint xi = flyI.getCentroids().get(d);
            DataPoint xj = flyJ.getCentroids().get(d);

            double[] newCoordinates = new double[xi.getCoordinates().length];
            for(int i = 0; i < xi.getCoordinates().length; i++){

                double rij = xi.distance(xj);
                double betaFirefly = this.beta * Math.exp(-this.gamma * rij * rij);

                newCoordinates[i] = xi.getCoordinates()[i] + betaFirefly * (xj.getCoordinates()[i] - xi.getCoordinates()[i]) + this.alpha * (random.nextDouble() - 0.5);
            }

            newCentroids.add(new DataPoint(newCoordinates));
        }

        flyI.setCentroids(newCentroids);
        flyI.setBrightness(calculateBrightness(newCentroids));
    }

    private Firefly findBestFirefly(List<Firefly> fireflies) {
        Firefly best = fireflies.getFirst();

        for(Firefly firefly : fireflies) {
            if(firefly.getBrightness() < best.getBrightness()) {
                best = firefly;
            }
        }

        return best;
    }

}
