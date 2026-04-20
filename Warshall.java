import java.util.*;

public class Warshall {

    static final int V = 10;

    public static void main(String[] args) {

        int[][] grafo = generarGrafo();

        System.out.println("Matriz de Adyacencia:");
        imprimirMatriz(grafo);

        boolean[][] alcance = warshall(grafo);

        System.out.println("\nMatriz de Alcance (Cierre Transitivo):");
        imprimirMatrizBool(alcance);

        if (esFuertementeConexo(alcance)) {
            System.out.println("\nEl grafo ES fuertemente conexo.");
        } else {
            System.out.println("\nEl grafo NO es fuertemente conexo.");
            List<List<Integer>> cfc = obtenerCFC(alcance);
            mostrarCFC(cfc);
        }
    }

    // Generar grafo dirigido (0 o 1)
    static int[][] generarGrafo() {
        Random rand = new Random();
        int[][] matriz = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    matriz[i][j] = 1;
                } else {
                    matriz[i][j] = rand.nextInt(2); // 0 o 1
                }
            }
        }
        return matriz;
    }

    static void imprimirMatriz(int[][] matriz) {
        for (int[] fila : matriz) {
            for (int val : fila) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    static void imprimirMatrizBool(boolean[][] matriz) {
        for (boolean[] fila : matriz) {
            for (boolean val : fila) {
                System.out.print((val ? 1 : 0) + " ");
            }
            System.out.println();
        }
    }

    // WARSHALL (cierre transitivo)
    static boolean[][] warshall(int[][] grafo) {
        boolean[][] alcance = new boolean[V][V];

        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                alcance[i][j] = grafo[i][j] == 1;
            }
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    alcance[i][j] = alcance[i][j] ||
                                    (alcance[i][k] && alcance[k][j]);
                }
            }
        }

        return alcance;
    }

    // Verificar si es fuertemente conexo
    static boolean esFuertementeConexo(boolean[][] alcance) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (!alcance[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // Obtener componentes fuertemente conexas (CFC)
    static List<List<Integer>> obtenerCFC(boolean[][] alcance) {
        boolean[] visitado = new boolean[V];
        List<List<Integer>> componentes = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            if (!visitado[i]) {
                List<Integer> componente = new ArrayList<>();

                for (int j = 0; j < V; j++) {
                    // Si i llega a j y j llega a i → misma CFC
                    if (!visitado[j] && alcance[i][j] && alcance[j][i]) {
                        componente.add(j);
                        visitado[j] = true;
                    }
                }

                componentes.add(componente);
            }
        }

        return componentes;
    }

    // Mostrar CFC
    static void mostrarCFC(List<List<Integer>> cfc) {
        System.out.println("\nComponentes Fuertemente Conexas:");

        int contador = 1;
        for (List<Integer> comp : cfc) {
            System.out.println("CFC" + contador + ": " + comp);
            contador++;
        }
    }
}
