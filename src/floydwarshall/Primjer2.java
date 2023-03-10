package floydwarshall;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.time.StopWatch;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.data.xy.DefaultXYDataset;

/**
 *
 * @author mraguzin
 */
public class Primjer2 {
    // ovaj primjer nasumično generira sve veće i veće grafove i nad njima
    // izvodi Floyd-Warshallov algoritam kako bismo
    // dobili empirijske podatke o asimptotskom vremenskom ponašanju algoritma
    private final Random r = new Random(0xbadc0ffeel);
    private final Random w = new Random(0xdeadbeefl);
    private final float p = 0.81f;
    
    public float[][] nasumičnaMatrica(int dim) {
        float[][] m = new float[dim][dim];
        for (int i = 0; i < dim; ++i) {
            for (int j = 0; j < dim; ++j) {
                if (i == j)
                    m[i][i] = 0;
                else if (r.nextFloat() < p)
                    m[i][j] = w.nextInt();
                else
                    m[i][j] = Float.POSITIVE_INFINITY;
            }
        }
        
        return m;
    }
    
    public static void main(String args[]) {
        //final int[] veličine = {10, 50, 250, 500, 1000, 5000, 10000};
        final int[] veličine = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200, 250, 300, 350, 400, 450, 500};
        final int n = 10; // broj ponavljanja izvršavanja algoritma za svaku veličinu
        Primjer2 p2 = new Primjer2();
        StopWatch sw = new StopWatch();
        var mjerenja = new double[veličine.length];
        
        for (int k = 0; k < veličine.length; ++k) {
            int dim = veličine[k];
            double t = 0;
            for (int i = 0; i < n; ++i) {
                sw.start();
                sw.suspend();
                float[][] m = p2.nasumičnaMatrica(dim);
                sw.resume();
                Graf graf = new Graf(m);
                graf.najkraćiPut(false);
                sw.stop();
                t += sw.getTime(TimeUnit.MILLISECONDS);
                sw.reset();
            }
            
            t /= n;
            mjerenja[k] = t;
        }
        
        var dataset = new DefaultXYDataset();
        var data = new double[2][veličine.length];
        var data2 = new double[2][veličine.length];
        double C0 = mjerenja[10] / Math.pow(veličine[10], 3);
        for (int i = 0; i < mjerenja.length; ++i) {
            data[0][i] = veličine[i];
            data[1][i] = mjerenja[i];
            data2[0][i] = veličine[i];
            data2[1][i] = Math.pow(veličine[i], 3) * C0;
        }
            
        dataset.addSeries("S(n)", data);
        dataset.addSeries("Cn³", data2);
        
        var chart = ChartFactory.createXYLineChart("Vremena izvođenja", "n", "t(ms)", dataset);
        try {
            ChartUtils.saveChartAsPNG(new File("vrijeme.png"), chart, 500, 500);
        } catch (IOException ex) {
            Logger.getLogger(Primjer2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
