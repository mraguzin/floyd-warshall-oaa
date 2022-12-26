package floydwarshall;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mraguzin
 */
public class FloydWarshall {

    public static void najkraćiPut(float m[][]) {
        // prema [1]
        int n = m.length;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (m[j][i] < Float.POSITIVE_INFINITY) {
                    for (int k = 0; k < n; ++k) {
                        if (m[i][k] < Float.POSITIVE_INFINITY) {
                            float s = m[j][i] + m[i][k];
                            if (s < m[j][k])
                                m[j][k] = s;
                        }
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) {
        final String odgovori[] = {"matrica", "lista"};
        float[][] m;
        
        Scanner s = new Scanner(new InputStreamReader(System.in));
        String odgovor = "";
        boolean dalje = true;
        
        while (dalje) {
            System.out.println("Kako želite unijeti graf? Mogući formati su: "
                + "matrica susjedstva i lista susjedstva. Odgovorite sa 'matrica'"
                + " ili 'lista'");
            odgovor = s.nextLine();
            for (var o : odgovori) {
                if (odgovor.equals(o))
                    dalje = false;
            }
        }
        
        if (odgovor.equals("matrica")) {
            System.out.print("Broj vrhova: ");
            int n = s.nextInt();
            m = new float[n][n];
            System.out.println();
            System.out.println("Za težinu ∞ upišite inf");
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i != j) {
                        System.out.print("(" + i + "," + j + "): ");
                        odgovor = s.nextLine();
                        if (odgovor.equals("inf"))
                            m[i][j] = Float.POSITIVE_INFINITY;
                        else
                            m[i][j] = Float.parseFloat(odgovor);
                    }
                }
            }
        }
        
        else {
            System.out.println("Upisujte trojke u obliku (prvi vrh,drugi vrh,"
                    + "težina). Za kraj, upišite 'kraj'");
            var lista = new ArrayList<Trojka<Integer,Integer,Float>>();
            while (true) {
                odgovor = s.nextLine();
                if (odgovor.equals("kraj"))
                    break;
                
                Pattern p = Pattern.compile("\\((\\d+),(\\d+),(.)\\)");
                Matcher matcher = p.matcher(odgovor);
                matcher.find();
                Trojka<Integer,Integer,Float> t = new Trojka<>();
                t.prvi = Integer.parseInt(matcher.group(1));
                t.drugi = Integer.parseInt(matcher.group(2));
                t.treći = Float.parseFloat(matcher.group(3));
                
                lista.add(t);
            }
            
            Graf graf = new Graf(lista);
            m = graf.dajMatricuSusjednosti();
        }
        
        najkraćiPut(m);
    }
    
}
