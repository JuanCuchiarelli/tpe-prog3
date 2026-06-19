import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Servicios {
    
    private HashMap<String, Paquete> paquetePorCodigo;
    private ArrayList<Paquete> paquetesConAlimentos;
    private ArrayList<Paquete> paqueteSinAlimentos;
    private TreeMap<Integer, List<Paquete>> paquetesPorUrgencia;
    private ArrayList<Camion> camiones;
    private ArrayList<Paquete> paquetes;


    private ArrayList<Paquete> leerPaquetes(String path) throws FileNotFoundException {
        //1. Crear un ArrayList<Paquete> vacío para ir guardando los paquetes
        ArrayList<Paquete> paquetes  = new ArrayList<>();
        //2. Crear un Scanner que apunte al archivo en el path recibido
        Scanner scanner = new Scanner(new File(path));
        //3. Leer y descartar la primera línea (es la cantidad total)
        scanner.nextLine();
        //4. Mientras haya líneas:
        while(scanner.hasNextLine()){
            //a. Leer la línea
                String linea = scanner.nextLine(); 
            //b. Separar por ";" 
                String[] paquete = linea.split(";");
            //c. Convertir cada parte al tipo correcto
                int id = Integer.parseInt(paquete[0]);
                String codigo = paquete[1];
                double peso = Double.parseDouble(paquete[2]);
                boolean contieneAlimento = paquete[3].equals("1");
                int nivelUrgencia = Integer.parseInt(paquete[4]);
            //d. Crear el objeto Paquete
                Paquete nuevoPaquete = new Paquete(id, codigo, peso, contieneAlimento, nivelUrgencia);
            //e. Agregarlo al arraylist
                paquetes.add(nuevoPaquete);
            }
        //5. Cerrar el Scanner
        scanner.close();
        //6. Retornar el arrayList
        return paquetes; 
    }

    private ArrayList<Camion> leerCamiones(String path) throws FileNotFoundException{
        ArrayList<Camion> camiones = new ArrayList<>();
        Scanner scanner = new Scanner(new File(path));
        scanner.nextLine();
        while(scanner.hasNextLine()){
            String linea = scanner.nextLine();
            String[] camion = linea.split(";");
            int id = Integer.parseInt(camion[0]);
            String patente = camion[1];
            boolean estaRefrigerado = camion[2].equals("1");
            double capacidad = Double.parseDouble(camion[3]);
            Camion nuevoCamion = new Camion(id, patente, estaRefrigerado, capacidad);
            camiones.add(nuevoCamion);
        }
        scanner.close();
        return camiones;
    }

    /*
     * Complejidad: O(P + C). Se lee cada archivo una sola vez (O(C) y O(P))
     * y luego se recorre la lista de paquetes una vez más para poblar las
     * estructuras (HashMap, ArrayLists, TreeMap), donde cada inserción es O(1) promedio.
     */
    public Servicios(String pathCamiones, String pathPaquetes){
        try {
            this.paquetePorCodigo = new HashMap<>();
            this.paquetesConAlimentos = new ArrayList<>();
            this.paqueteSinAlimentos = new ArrayList<>();
            this.paquetesPorUrgencia = new TreeMap<>();
            this.camiones = leerCamiones(pathCamiones);
            this.paquetes = leerPaquetes(pathPaquetes);

            for(Paquete p : paquetes){
                paquetePorCodigo.put(p.getCodigo(), p);
                if(p.getContieneAlimento()){
                    paquetesConAlimentos.add(p);
                } else {
                    paqueteSinAlimentos.add(p);
                }
                //agregarlos al treemap
                if(!paquetesPorUrgencia.containsKey(p.getNivelUrgencia())){
                    ArrayList<Paquete> lista = new ArrayList<>();
                    lista.add(p);
                    paquetesPorUrgencia.put(p.getNivelUrgencia(), lista);
                } else {
                    paquetesPorUrgencia.get(p.getNivelUrgencia()).add(p);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: archivo no encontrado");
        }
    }

    /*
     * Complejidad: O(1) promedio. Búsqueda directa en HashMap por clave.
     */
    public Paquete servicio1(String codigoPaquete){
        return this.paquetePorCodigo.get(codigoPaquete);
    }

    /*
     * Complejidad: O(k), donde k es la cantidad de paquetes en la categoría
     * solicitada. Las listas ya están armadas desde el constructor, solo se
     * copian para no exponer el atributo interno (encapsulamiento).
     */
    public List<Paquete> servicio2(boolean contieneAlimentos){
        if(contieneAlimentos){
            return new ArrayList<>(paquetesConAlimentos);
        } else {
            return new ArrayList<>(paqueteSinAlimentos);
        }
    }

    /*
     * Complejidad: O(log n + k), donde k es la cantidad de paquetes en el rango.
     * subMap() aprovecha el árbol balanceado del TreeMap para ubicar el rango
     * directamente en O(log n), y luego se recorren solo los k resultados.
     */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima){
        ArrayList<Paquete> resultado = new ArrayList<>();
        for(List<Paquete> lista : paquetesPorUrgencia.subMap(urgenciaMinima, true, urgenciaMaxima, true).values()){
            resultado.addAll(lista);
        }
        return resultado;
    }
    
    public ArrayList<Camion> getCamiones() {
        ArrayList<Camion> salida = new ArrayList<>(this.camiones);
        return salida;
    }
    
    public ArrayList<Paquete> getPaquetes(){
        ArrayList<Paquete> salida = new ArrayList<>(this.paquetes);
        return salida;
    }
}