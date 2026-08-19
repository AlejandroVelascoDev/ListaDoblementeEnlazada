import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        // "Problema real": gestor de una lista de reproducción (playlist) de canciones.
        // Se usa una lista doblemente enlazada porque en una playlist es habitual
        // moverse hacia delante y hacia atrás, insertar canciones nuevas al final,
        // quitar una canción por nombre o por posición, comprobar si una canción
        // ya está en la lista, reemplazar una canción y fusionar dos playlists.

        Main gestor = new Main();
        ListaDoblementeEnlazada playlist = gestor.new ListaDoblementeEnlazada();
        Scanner scanner = new Scanner(System.in);

        int opcion;

        do {
            System.out.println("\n===== GESTOR DE PLAYLIST (Lista Doblemente Enlazada) =====");
            System.out.println("1. Agregar canción");
            System.out.println("2. Contar canciones");
            System.out.println("3. Mostrar canción en una posición");
            System.out.println("4. Comprobar si una canción está en la playlist");
            System.out.println("5. Imprimir todas las canciones");
            System.out.println("6. Eliminar canción por nombre");
            System.out.println("7. Eliminar canción por posición");
            System.out.println("8. Concatenar con otra playlist (demo)");
            System.out.println("9. Reemplazar una canción");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = leerEntero(scanner);

            switch (opcion) {
                case 1 -> {
                    System.out.print("Nombre de la canción a agregar: ");
                    String cancion = scanner.nextLine();
                    playlist.agregarNodo(cancion);
                    System.out.println("Canción agregada correctamente.");
                }
                case 2 -> System.out.println("Número de canciones en la playlist: " + playlist.contadorElementos());
                case 3 -> {
                    System.out.print("Posición a consultar (empieza en 0): ");
                    int pos = leerEntero(scanner);
                    String elemento = playlist.mostrarElemento(pos);
                    if (elemento != null) {
                        System.out.println("Canción en la posición " + pos + ": " + elemento);
                    } else {
                        System.out.println("No existe ninguna canción en esa posición.");
                    }
                }
                case 4 -> {
                    System.out.print("Nombre de la canción a buscar: ");
                    String buscada = scanner.nextLine();
                    System.out.println(playlist.contiene(buscada)
                            ? "La canción SÍ está en la playlist."
                            : "La canción NO está en la playlist.");
                }
                case 5 -> System.out.println("Canciones en la playlist: " + playlist.elementos());
                case 6 -> {
                    System.out.print("Nombre de la canción a eliminar: ");
                    String aEliminar = scanner.nextLine();
                    System.out.println(playlist.eliminarPorValor(aEliminar)
                            ? "Canción eliminada."
                            : "No se encontró esa canción.");
                }
                case 7 -> {
                    System.out.print("Posición a eliminar (empieza en 0): ");
                    int posEliminar = leerEntero(scanner);
                    String eliminado = playlist.eliminarPorPosicion(posEliminar);
                    System.out.println(eliminado != null
                            ? "Se eliminó la canción: " + eliminado
                            : "No existe ninguna canción en esa posición.");
                }
                case 8 -> {
                    // Se crea una segunda playlist de ejemplo para mostrar la concatenación
                    ListaDoblementeEnlazada otraPlaylist = gestor.new ListaDoblementeEnlazada();
                    otraPlaylist.agregarNodo("Canción extra 1");
                    otraPlaylist.agregarNodo("Canción extra 2");
                    ListaDoblementeEnlazada resultado = playlist.concatenar(otraPlaylist);
                    System.out.println("Playlist original: " + playlist.elementos());
                    System.out.println("Playlist a concatenar: " + otraPlaylist.elementos());
                    System.out.println("Resultado de la concatenación: " + resultado.elementos());
                }
                case 9 -> {
                    System.out.print("Nombre de la canción que quieres reemplazar: ");
                    String antigua = scanner.nextLine();
                    System.out.print("Nuevo nombre de la canción: ");
                    String nueva = scanner.nextLine();
                    System.out.println(playlist.reemplazarPorValor(antigua, nueva)
                            ? "Canción reemplazada correctamente."
                            : "No se encontró la canción a reemplazar.");
                }
                case 0 -> System.out.println("¡Hasta luego!");
                default -> System.out.println("Opción no válida, inténtalo de nuevo.");
            }

        } while (opcion != 0);

        scanner.close();
    }

    // Método auxiliar para leer un entero de forma segura y no romper el Scanner
    private static int leerEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, introduce un número válido.");
            scanner.next();
        }
        int valor = scanner.nextInt();
        scanner.nextLine(); // consumimos el salto de línea pendiente
        return valor;
    }

    // Clase nodo
    public class Nodo {
        private String dato;
        private Nodo siguiente;
        private Nodo anterior;

        public Nodo(String dato) {
            this.dato = dato;
            this.siguiente = null;
            this.anterior = null;
        }

        public void setDato(String dato) {
            this.dato = dato;
        }

        public String getDato() {
            return this.dato;
        }
    }

    // Clase lista doblemente enlazada
    public class ListaDoblementeEnlazada {
        private Nodo primero;
        private Nodo ultimo;

        // funcion agregarNodo (insertar un elemento nuevo al final de la lista)
        public void agregarNodo(String dato) {
            Nodo nuevoNodo = new Nodo(dato);
            // si primero es null significa que esta vacia la lista por lo tanto el nuevo nodo es el primero y ultimo
            if (primero == null) {
                primero = ultimo = nuevoNodo;
            } else {
                // el anterior nodo de el nuevo es el ultimo
                nuevoNodo.anterior = this.ultimo;
                // el siguiente del nodo que actualmente es el último será el nuevo nodo
                this.ultimo.siguiente = nuevoNodo;
                // el ultimo ahora es el nuevoNodo
                this.ultimo = nuevoNodo;
            }
        }

        // funcion contar elementos de la lista
        public int contadorElementos() {
            // variable para contar los elementos
            int contador = 0;
            // actual es el primer Nodo
            Nodo actual = primero;

            // creamos un bucle para que cuando actual no sea igual a null  sume 1 y pase al nodo siguiente
            while (actual != null) {
                contador++;
                actual = actual.siguiente;
            }
            // retornamos variable contador
            return contador;
        }

        // funcion para obtener el nodo que hay en una posicion concreta
        public Nodo obtenerNodo(int posicion) {

            if (posicion < 0) {
                return null;
            }

            Nodo actual = primero;

            for (int contador = 0; contador < posicion; contador++) {
                if (actual == null) {
                    return null;
                }
                actual = actual.siguiente;
            }
            return actual;
        }

        // funcion para mostrar (como String) el elemento que hay en una posicion concreta
        public String mostrarElemento(int posicion) {
            Nodo nodo = obtenerNodo(posicion);
            return (nodo != null) ? nodo.getDato() : null;
        }

        // funcion para comprobar si hay un elemento en la lista
        public boolean contiene(String dato) {
            Nodo actual = primero;
            while (actual != null) {
                if (actual.dato.equals(dato)) {
                    return true;
                }
                actual = actual.siguiente;
            }
            return false;
        }

        // imprimir elementos que tiene la lista
        public ArrayList<String> elementos() {
            Nodo actual = primero;
            ArrayList<String> elementosLista = new ArrayList<>();

            while (actual != null) {
                elementosLista.add(actual.dato);
                actual = actual.siguiente;
            }

            return elementosLista;
        }

        // funcion para sacar (eliminar) un elemento concreto de la lista, buscando por su valor
        public boolean eliminarPorValor(String dato) {
            Nodo actual = primero;

            while (actual != null) {
                if (actual.dato.equals(dato)) {
                    desenlazarNodo(actual);
                    return true;
                }
                actual = actual.siguiente;
            }
            return false;
        }

        // funcion para sacar (eliminar) el elemento que ocupa una posicion concreta en la lista
        public String eliminarPorPosicion(int posicion) {
            Nodo nodo = obtenerNodo(posicion);
            if (nodo == null) {
                return null;
            }
            String datoEliminado = nodo.dato;
            desenlazarNodo(nodo);
            return datoEliminado;
        }

        // funcion auxiliar que desconecta un nodo de la lista recolocando los punteros
        // anterior/siguiente de sus vecinos (o actualizando primero/ultimo si hace falta)
        private void desenlazarNodo(Nodo nodo) {
            if (nodo.anterior != null) {
                nodo.anterior.siguiente = nodo.siguiente;
            } else {
                // el nodo era el primero de la lista
                primero = nodo.siguiente;
            }

            if (nodo.siguiente != null) {
                nodo.siguiente.anterior = nodo.anterior;
            } else {
                // el nodo era el ultimo de la lista
                ultimo = nodo.anterior;
            }

            // limpiamos las referencias del nodo eliminado
            nodo.siguiente = null;
            nodo.anterior = null;
        }

        // funcion para reemplazar el elemento que ocupa una posicion concreta
        public boolean reemplazarPorPosicion(int posicion, String nuevoDato) {
            Nodo nodo = obtenerNodo(posicion);
            if (nodo == null) {
                return false;
            }
            nodo.setDato(nuevoDato);
            return true;
        }

        // funcion para reemplazar la primera coincidencia de un elemento por otro valor
        public boolean reemplazarPorValor(String datoAntiguo, String datoNuevo) {
            Nodo actual = primero;
            while (actual != null) {
                if (actual.dato.equals(datoAntiguo)) {
                    actual.setDato(datoNuevo);
                    return true;
                }
                actual = actual.siguiente;
            }
            return false;
        }

        // funcion para concatenar esta lista con otra lista, devolviendo una lista NUEVA
        // (se crean nodos nuevos con los mismos datos para no compartir referencias
        // entre las dos listas originales, evitando así que se corrompan entre sí)
        public ListaDoblementeEnlazada concatenar(ListaDoblementeEnlazada otraLista) {
            ListaDoblementeEnlazada listaResultado = new ListaDoblementeEnlazada();

            Nodo actual = this.primero;
            while (actual != null) {
                listaResultado.agregarNodo(actual.dato);
                actual = actual.siguiente;
            }

            actual = otraLista.primero;
            while (actual != null) {
                listaResultado.agregarNodo(actual.dato);
                actual = actual.siguiente;
            }

            return listaResultado;
        }

        @Override
        public String toString() {
            return elementos().toString();
        }
    }
}