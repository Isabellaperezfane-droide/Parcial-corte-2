import java.util.*;

public class Dijkstra {

    static final int V = 10;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {

        int[][] grafo = generarGrafo();

        System.out.println("Matriz de Adyacencia:");
        imprimirMatriz(grafo);

        int verticeCentral = -1;
        int menorSuma = INF;

        // Ejecutar Dijkstra desde cada vértice
        for (int i = 0; i < V; i++) {
            int[] distancias = dijkstra(grafo, i);

            int suma = 0;
            for (int d : distancias) {
                if (d != INF) {
                    suma += d;
                }
            }

            System.out.println("\nDesde el vértice " + i + ":");
            System.out.println("Distancias: " + Arrays.toString(distancias));
            System.out.println("Suma total: " + suma);

            // verificar vértice más central
            if (suma < menorSuma) {
                menorSuma = suma;
                verticeCentral = i;
            }
        }

        System.out.println("\n--- RESULTADO FINAL ---");
        System.out.println("Vértice más central: " + verticeCentral);
        System.out.println("Suma mínima de distancias: " + menorSuma);
    }

    // Generar grafo con pesos mixtos
    static int[][] generarGrafo() {
        Random rand = new Random();
        int[][] matriz = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                } else {
                    // pesos entre 1 y 100 (mezcla de altos y bajos)
                    matriz[i][j] = rand.nextInt(100) + 1;
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

    // DIJKSTRA desde un origen
    static int[] dijkstra(int[][] grafo, int origen) {
        int[] dist = new int[V];
        boolean[] visitado = new boolean[V];

        Arrays.fill(dist, INF);
        dist[origen] = 0;

        for (int i = 0; i < V - 1; i++) {
            int u = minimo(dist, visitado);
            if (u == -1) break;

            visitado[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visitado[v] && grafo[u][v] != INF && dist[u] != INF) {
                    if (dist[u] + grafo[u][v] < dist[v]) {
                        dist[v] = dist[u] + grafo[u][v];
                    }
                }
            }
        }

        return dist;
    }

    static int minimo(int[] dist, boolean[] visitado) {
        int min = INF, indice = -1;

        for (int i = 0; i < V; i++) {
            if (!visitado[i] && dist[i] < min) {
                min = dist[i];
                indice = i;
            }
        }
        return indice;
    }
}
