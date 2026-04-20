import java.util.*;

class Arista implements Comparable<Arista> {
    int origen, destino, peso;

    public Arista(int o, int d, int p) {
        this.origen = o;
        this.destino = d;
        this.peso = p;
    }

    public int compareTo(Arista otra) {
        return this.peso - otra.peso;
    }

    public String toString() {
        return origen + " - " + destino + " (" + peso + ")";
    }
}

public class Kruskal {

    static final int V = 10;

    public static void main(String[] args) {
        List<Arista> aristas = generarAristas();

        // 1. Ordenar aristas
        Collections.sort(aristas);

        System.out.println("Aristas ordenadas (incluye negativos):");
        for (Arista a : aristas) {
            System.out.println(a);
        }

        kruskal(aristas);
    }

    // Generar aristas con pesos negativos y positivos
    static List<Arista> generarAristas() {
        Random rand = new Random();
        List<Arista> lista = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                int peso = rand.nextInt(201) - 100; // rango [-100, 100]
                lista.add(new Arista(i, j, peso));
            }
        }
        return lista;
    }

    static void kruskal(List<Arista> aristas) {
        int[] padre = new int[V];
        for (int i = 0; i < V; i++) padre[i] = i;

        List<Arista> mst = new ArrayList<>();
        int ciclosDetectados = 0;
        int costoTotal = 0;

        for (Arista a : aristas) {
            int raiz1 = encontrar(padre, a.origen);
            int raiz2 = encontrar(padre, a.destino);

            if (raiz1 != raiz2) {
                mst.add(a);
                costoTotal += a.peso;
                padre[raiz1] = raiz2;
            } else {
                // ciclo detectado
                ciclosDetectados++;
            }

            if (mst.size() == V - 1) break;
        }

        System.out.println("\n--- Árbol de Expansión Mínima (MST) ---");
        for (Arista a : mst) {
            System.out.println(a);
        }

        System.out.println("\nCosto total del MST: " + costoTotal);
        System.out.println("Ciclos detectados y descartados: " + ciclosDetectados);
    }

    static int encontrar(int[] padre, int i) {
        if (padre[i] == i) return i;
        return padre[i] = encontrar(padre, padre[i]);
    }
}
