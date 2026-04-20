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

public class CostoMinimo {

    static final int V = 10;

    public static void main(String[] args) {
        int[][] grafo = generarMatriz();

        System.out.println("Matriz de Adyacencia:");
        imprimirMatriz(grafo);

        List<Arista> primMST = prim(grafo);
        List<Arista> kruskalMST = kruskal(grafo);

        int costoPrim = calcularCosto(primMST);
        int costoKruskal = calcularCosto(kruskalMST);

        System.out.println("\n--- PRIM ---");
        primMST.forEach(System.out::println);
        System.out.println("Costo total: " + costoPrim);

        System.out.println("\n--- KRUSKAL ---");
        kruskalMST.forEach(System.out::println);
        System.out.println("Costo total: " + costoKruskal);

        compararAristas(primMST, kruskalMST);
    }

    // Generar matriz aleatoria
    static int[][] generarMatriz() {
        Random rand = new Random();
        int[][] matriz = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = i; j < V; j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                } else {
                    int peso = rand.nextInt(100) + 1;
                    matriz[i][j] = matriz[j][i] = peso;
                }
            }
        }
        return matriz;
    }

    static void imprimirMatriz(int[][] matriz) {
        for (int[] fila : matriz) {
            for (int val : fila) {
                System.out.printf("%4d", val);
            }
            System.out.println();
        }
    }

    // PRIM
    static List<Arista> prim(int[][] grafo) {
        boolean[] visitado = new boolean[V];
        List<Arista> resultado = new ArrayList<>();
        visitado[0] = true;

        for (int i = 0; i < V - 1; i++) {
            int min = Integer.MAX_VALUE;
            int u = -1, v = -1;

            for (int j = 0; j < V; j++) {
                if (visitado[j]) {
                    for (int k = 0; k < V; k++) {
                        if (!visitado[k] && grafo[j][k] < min) {
                            min = grafo[j][k];
                            u = j;
                            v = k;
                        }
                    }
                }
            }

            visitado[v] = true;
            resultado.add(new Arista(u, v, min));
        }

        return resultado;
    }

    // KRUSKAL
    static List<Arista> kruskal(int[][] grafo) {
        List<Arista> aristas = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            for (int j = i + 1; j < V; j++) {
                aristas.add(new Arista(i, j, grafo[i][j]));
            }
        }

        Collections.sort(aristas);

        int[] padre = new int[V];
        for (int i = 0; i < V; i++)
            padre[i] = i;

        List<Arista> resultado = new ArrayList<>();

        for (Arista a : aristas) {
            int raiz1 = encontrar(padre, a.origen);
            int raiz2 = encontrar(padre, a.destino);

            if (raiz1 != raiz2) {
                resultado.add(a);
                padre[raiz1] = raiz2;
            }

            if (resultado.size() == V - 1)
                break;
        }

        return resultado;
    }

    static int encontrar(int[] padre, int i) {
        if (padre[i] == i)
            return i;
        return padre[i] = encontrar(padre, padre[i]);
    }

    static int calcularCosto(List<Arista> mst) {
        int suma = 0;
        for (Arista a : mst)
            suma += a.peso;
        return suma;
    }

    // Comparar aristas
    static void compararAristas(List<Arista> prim, List<Arista> kruskal) {
        Set<String> setPrim = new HashSet<>();
        Set<String> setKruskal = new HashSet<>();

        for (Arista a : prim)
            setPrim.add(a.origen + "-" + a.destino);

        for (Arista a : kruskal)
            setKruskal.add(a.origen + "-" + a.destino);

        if (setPrim.equals(setKruskal)) {
            System.out.println("\nAmbos algoritmos generaron el MISMO conjunto de aristas.");
        } else {
            System.out.println("\nLos algoritmos generaron DIFERENTES aristas.");

            Set<String> soloPrim = new HashSet<>(setPrim);
            soloPrim.removeAll(setKruskal);

            Set<String> soloKruskal = new HashSet<>(setKruskal);
            soloKruskal.removeAll(setPrim);

            System.out.println("Solo en Prim: " + soloPrim);
            System.out.println("Solo en Kruskal: " + soloKruskal);
        }
    }
}