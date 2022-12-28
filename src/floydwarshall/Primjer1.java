package floydwarshall;

/**
 *
 * @author mraguzin
 */
public class Primjer1 {
    
    public static void main(String args[]) {
        Graf graf = new Graf(new float[][]{{0f, 3f, 8f, Float.POSITIVE_INFINITY, -4f},
            {Float.POSITIVE_INFINITY, 0f, Float.POSITIVE_INFINITY, 1f, 7f},
            {Float.POSITIVE_INFINITY, 4f, 0f, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY},
            {2f, Float.POSITIVE_INFINITY, -5f, 0f, Float.POSITIVE_INFINITY},
            {Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, 6f, 0f}
            }
            );
        
        var m = graf.najkraćiPut(true);
        System.out.println("Konačno rješenje:");
        Graf.ispišiMatricu(m);
    }
}
