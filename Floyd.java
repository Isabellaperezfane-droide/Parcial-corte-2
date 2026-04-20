import java.util.*;

public class Floyd {

    static final int V = 10;
    static final int INF = 999999;

    public static void main(String[] args) {

        int[][] grafo = generarGrafo();

        System.out.println("Matriz de Adyacencia Inicial:");
        imprimirMatriz(grafo);

        int[][] dist = floydWarshall(grafo);

        System.out.println("\nMatriz de distancias mínimas:");
        imprimirMatriz(dist);

        if (hayCicloNegativo(dist)) {
            System.out.println("\n⚠ Se detectaron ciclos negativos.");

            eliminarCiclosNegativos(grafo, dist);

            System.out.println("\nRecalculando sin ciclos negativos...\n");

            int[][] nuevaDist = floydWarshall(grafo);

            System.out.println("Nueva matriz de distancias:");
            imprimirMatriz(nuevaDist);

        } else {
            System.out.println("\nNo hay ciclos negativos.");
        }
    }

    // Generar grafo dirigido con pesos positivos y negativos
    static int[][] generarGrafo() {
        Random rand = new Random();
        int[][] matriz = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                } else {
                    if (rand.nextBoolean()) {
                        matriz[i][j] = rand.nextInt(41) - 20; // [-20, 20]
                    } else {
                        matriz[i][j] = INF;
                    }
                }
            }
        }
        return matriz;
    }

    static void imprimirMatriz(int[][] matriz) {
        for (int[] fila : matriz) {
            for (int val : fila) {
                if (val == INF)
                    System.out.printf("%6s", "∞");
                else
                    System.out.printf("%6d", val);
            }
            System.out.println();
        }
    }

    // FLOYD-WARSHALL
    static int[][] floydWarshall(int[][] grafo) {
        int[][] dist = new int[V][V];

        // copiar matriz
        for (int i = 0; i < V; i++)
            dist[i] = Arrays.copyOf(grafo[i], V);

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {

                    if (dist[i][k] != INF && dist[k][j] != INF &&
                        dist[i][k] + dist[k][j] < dist[i][j]) {

                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        return dist;
    }

    // Detectar ciclos negativos
    static boolean hayCicloNegativo(int[][] dist) {
        for (int i = 0; i < V; i++) {
            if (dist[i][i] < 0) {
                System.out.println("Ciclo negativo detectado en vértice: " + i);
                return true;
            }
        }
        return false;
    }

    // Eliminar aristas implicadas en ciclos negativos
    static void eliminarCiclosNegativos(int[][] grafo, int[][] dist) {

        for (int i = 0; i < V; i++) {
            if (dist[i][i] < 0) {
                System.out.println("Eliminando conexiones del vértice " + i);

                for (int j = 0; j < V; j++) {
                    grafo[i][j] = INF;
                    grafo[j][i] = INF;
                }

                grafo[i][i] = 0;
            }
        }
    }
}
