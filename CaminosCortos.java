import java.util.*;

public class CaminosCortos {

    static final int V = 10;
    static final int INF = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[][] grafo = generarGrafoDirigido();

        System.out.println("Matriz de Adyacencia (grafo dirigido):");
        imprimirMatriz(grafo);

        System.out.print("\nIngrese vértice origen (0-9): ");
        int origen = sc.nextInt();

        System.out.print("Ingrese vértice destino (0-9): ");
        int destino = sc.nextInt();

        System.out.print("Ingrese valor X: ");
        int X = sc.nextInt();

        dijkstra(grafo, origen, destino, X);
    }

    // Generar grafo dirigido con algunos caminos (otros INF)
    static int[][] generarGrafoDirigido() {
        Random rand = new Random();
        int[][] matriz = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    matriz[i][j] = 0;
                } else {
                    // 50% probabilidad de que exista arista
                    if (rand.nextBoolean()) {
                        matriz[i][j] = rand.nextInt(20) + 1; // pesos 1-20
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
                    System.out.printf("%4s", "∞");
                else
                    System.out.printf("%4d", val);
            }
            System.out.println();
        }
    }

    // DIJKSTRA
    static void dijkstra(int[][] grafo, int origen, int destino, int X) {
        int[] dist = new int[V];
        boolean[] visitado = new boolean[V];
        int[] previo = new int[V];

        Arrays.fill(dist, INF);
        Arrays.fill(previo, -1);

        dist[origen] = 0;

        for (int i = 0; i < V - 1; i++) {
            int u = minimo(dist, visitado);
            if (u == -1) break;

            visitado[u] = true;

            for (int v = 0; v < V; v++) {
                if (!visitado[v] && grafo[u][v] != INF && dist[u] != INF) {
                    if (dist[u] + grafo[u][v] < dist[v]) {
                        dist[v] = dist[u] + grafo[u][v];
                        previo[v] = u;
                    }
                }
            }
        }

        if (dist[destino] < X) {
            System.out.println("\nExiste un camino con costo menor que " + X);
            System.out.println("Costo: " + dist[destino]);

            List<Integer> ruta = reconstruirRuta(previo, destino);
            System.out.println("Ruta: " + ruta);

        } else {
            System.out.println("\nNo existe un camino con costo menor que " + X);
        }
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

    // reconstruir ruta
    static List<Integer> reconstruirRuta(int[] previo, int destino) {
        List<Integer> ruta = new ArrayList<>();

        for (int at = destino; at != -1; at = previo[at]) {
            ruta.add(at);
        }

        Collections.reverse(ruta);
        return ruta;
    }
}
