import java.util.*;

public class AplicacionGrafos {

    // Grafo representado con lista de adyacencia
    static Map<String, List<String>> grafo = new HashMap<>();

    public static void main(String[] args) {

        // Crear usuarios y relaciones
        agregarAmistad("Ana", "Luis");
        agregarAmistad("Ana", "Carlos");
        agregarAmistad("Luis", "Pedro");
        agregarAmistad("Carlos", "Sofia");
        agregarAmistad("Pedro", "Maria");
        agregarAmistad("Sofia", "Juan");

        System.out.println("Grafo de amistades:");
        mostrarGrafo();

        // Recomendaciones
        System.out.println("\nRecomendaciones para Ana:");
        System.out.println(recomendarAmigos("Ana"));

        // Ruta más corta
        System.out.println("\nRuta más corta entre Ana y Maria:");
        int distancia = rutaMasCorta("Ana", "Maria");

        if (distancia != -1) {
            System.out.println("Número mínimo de conexiones: " + distancia);
        } else {
            System.out.println("No hay conexión entre los usuarios.");
        }
    }

    // Agregar amistad (grafo no dirigido)
    static void agregarAmistad(String u1, String u2) {
        grafo.putIfAbsent(u1, new ArrayList<>());
        grafo.putIfAbsent(u2, new ArrayList<>());

        grafo.get(u1).add(u2);
        grafo.get(u2).add(u1);
    }

    // Mostrar grafo
    static void mostrarGrafo() {
        for (String usuario : grafo.keySet()) {
            System.out.println(usuario + " -> " + grafo.get(usuario));
        }
    }

    // Método de recomendación (amigos de amigos)
    static List<String> recomendarAmigos(String usuario) {
        Set<String> recomendaciones = new HashSet<>();

        List<String> amigos = grafo.getOrDefault(usuario, new ArrayList<>());

        for (String amigo : amigos) {
            List<String> amigosDeAmigo = grafo.get(amigo);

            for (String posible : amigosDeAmigo) {
                if (!posible.equals(usuario) && !amigos.contains(posible)) {
                    recomendaciones.add(posible);
                }
            }
        }

        return new ArrayList<>(recomendaciones);
    }

    // Ruta más corta usando BFS
    static int rutaMasCorta(String inicio, String destino) {

        if (!grafo.containsKey(inicio) || !grafo.containsKey(destino)) {
            return -1;
        }

        Queue<String> cola = new LinkedList<>();
        Map<String, Integer> distancia = new HashMap<>();

        cola.add(inicio);
        distancia.put(inicio, 0);

        while (!cola.isEmpty()) {
            String actual = cola.poll();

            if (actual.equals(destino)) {
                return distancia.get(actual);
            }

            for (String vecino : grafo.get(actual)) {
                if (!distancia.containsKey(vecino)) {
                    distancia.put(vecino, distancia.get(actual) + 1);
                    cola.add(vecino);
                }
            }
        }

        return -1; // no hay conexión
    }
}
