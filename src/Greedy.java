import java.util.ArrayList;
import java.util.Collections;

public class Greedy {
    private ArrayList<Camion> camiones;
    private ArrayList<Paquete> paquetes;
    private int candidatosConsiderados;
    private double pesoTotal;

    public Greedy(ArrayList<Camion> camiones, ArrayList<Paquete> paquetes){
        this.camiones = camiones;
        this.paquetes = paquetes;
        this.candidatosConsiderados = 0;
        this.pesoTotal = 0;
        for(Paquete p : paquetes){
            this.pesoTotal += p.getPeso();
        }
    }

    /*
     * Estrategia: se ordenan los paquetes de mayor a menor peso y se asigna
     * cada uno al primer camión válido disponible. 
     * Complejidad O(n log n).
     */
    public ArrayList<Camion> solucionGreedy(){
        // ordenar paquetes de mayor a menor peso
        Collections.sort(paquetes, (a, b) -> Double.compare(b.getPeso(), a.getPeso()));
        // para cada paquete, buscar el primer camión válido y asignarlo
        for(Paquete p : paquetes){
            for(Camion c : camiones){
                candidatosConsiderados++;
                if(c.puedeAsignar(p)){
                    c.agregarPaquete(p);
                    break; // encontró camión, pasá al siguiente paquete
                }
            }
        }
        return camiones;
    }

    public int getCandidatosConsiderados(){
        return candidatosConsiderados;
    }

    public double getPesoNoAsignado(){
        double pesoAsignado = 0;
        for(Camion c : camiones){
            pesoAsignado += c.getPesoActual();
        }
        return pesoTotal - pesoAsignado;
    }
}