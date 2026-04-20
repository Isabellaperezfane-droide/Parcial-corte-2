import java.util.*;

public class Prim {

    static final int V = 10;
    static int comparaciones = 0;

    public static void main(String[] args) {
        int[][] grafo = generarMatriz();

        System.out.println("Matriz de Adyacencia:");
        imprimirMatriz(grafo);

        prim(grafo);
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

    // PRIM MODIFICADO
    static void prim(int[][] grafo) {
        boolean[] visitado = new boolean[V];
        int costoTotal = 0;

        List<Integer> ordenVertices = new ArrayList<>();

        visitado[0] = true;
        ordenVertices.add(0);

        for (int i = 0; i < V - 1; i++) {
            int min = Integer.MAX_VALUE;
            int u = -1, v = -1;

            for (int j = 0; j < V; j++) {
                if (visitado[j]) {
                    for (int k = 0; k < V; k++) {
                        if (!visitado[k]) {

                            // contamos cada comparación de peso
                            comparaciones++;

                            if (grafo[j][k] < min) {
                                min = grafo[j][k];
                                u = j;
                                v = k;
                            }
                        }
                    }
                }
            }

            visitado[v] = true;
            ordenVertices.add(v);
            costoTotal += min;

            System.out.println("Se selecciona arista: " + u + " - " + v + " (" + min + ")");
        }

        System.out.println("\nOrden de selección de vértices:");
        System.out.println(ordenVertices);

        System.out.println("\nCosto total del MST: " + costoTotal);
        System.out.println("Cantidad total de comparaciones: " + comparaciones);
    }
}
