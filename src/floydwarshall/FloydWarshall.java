package floydwarshall;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mraguzin
 */
public class FloydWarshall {
    
    public static void main(String[] args) {
        final String odgovori[] = {"matrica", "lista"};
        float[][] m;
        Graf graf;
        
        Scanner s = new Scanner(System.in);
        String odgovor = "";
        boolean dalje = true;
        
        while (dalje) {
            System.out.println("Kako želite unijeti graf? Mogući formati su: "
                + "matrica susjedstva i lista susjedstva. Odgovorite sa 'matrica'"
                + " ili 'lista' (indeksi vrhova počinju s 1");
            odgovor = s.nextLine();
            for (var o : odgovori) {
                if (odgovor.equals(o))
                    dalje = false;
            }
        }
        
        if (odgovor.equals("matrica")) {
            System.out.print("Broj vrhova: ");
            int n = Integer.parseInt(s.nextLine());
            m = new float[n][n];
            System.out.println();
            System.out.println("Za težinu ∞ upišite inf");
            
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (i != j) {
                        System.out.print("(" + (i+1) + "," + (j+1) + "): ");
                        //while (!s.hasNextLine())
                        odgovor = s.nextLine();
                        if (odgovor.equals("inf"))
                            m[i][j] = Float.POSITIVE_INFINITY;
                        else try {
                            m[i][j] = Float.parseFloat(odgovor);
                        } catch (NumberFormatException e) {
                            System.out.println("Krivi unos");
                            j--;
                        }
                    }
                    System.out.println();
                }
            }
            
            graf = new Graf(m);
        }
        
        else {
            System.out.println("Upisujte trojke u obliku prvi vrh,drugi vrh,"
                    + "težina. Za kraj, upišite 'kraj'");
            var lista = new ArrayList<Trojka<Integer,Integer,Float>>();
            while (true) {
                odgovor = s.nextLine();
                if (odgovor.equals("kraj"))
                    break;
                
                Trojka<Integer,Integer,Float> t = new Trojka<>();
                var dijelovi = odgovor.split(",");
                try {
                t.prvi = Integer.parseInt(dijelovi[0]) - 1;
                t.drugi = Integer.parseInt(dijelovi[1]) - 1;
                t.treći = Float.valueOf(dijelovi[2]);
                } catch (NumberFormatException e) {
                    System.out.println("Krivi unos");
                    continue;
                }
                
                lista.add(t);
            }
            
            graf = new Graf(lista);
        }
        
        var putevi = graf.najkraćiPut(false);
        Graf.ispišiMatricu(putevi);
    }
    
}
