import java.util.ArrayList;

public class Camion {

    private int id;
    private String patente;
    private boolean estaRefrigerado;
    private double capacidad;
    private double pesoActual;
    

    private ArrayList<Paquete> paquetesAsignados;

    public Camion(int id, String patente, boolean estaRefrigerado, double capacidad){
        this.id = id;
        this.patente = patente;
        this.estaRefrigerado = estaRefrigerado;
        this.capacidad = capacidad;
        this.pesoActual = 0;
        this.paquetesAsignados = new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public String getPatente() {
        return patente;
    }
    public boolean estaRefrigerado() {
        return estaRefrigerado;
    }
    public double getCapacidad() {
        return capacidad;
    }
    public double getPesoActual() {
        return pesoActual;
    }

    public boolean puedeAsignar(Paquete p){
            if(p.getContieneAlimento()){
                // necesita refrigeración Y capacidad
                return estaRefrigerado && (pesoActual + p.getPeso() <= capacidad);
            } else {
                // solo necesita capacidad
                return pesoActual + p.getPeso() <= capacidad;
        }
    }

    public void agregarPaquete(Paquete p){
        this.paquetesAsignados.add(p);
        this.pesoActual += p.getPeso();
    }

    public void quitarPaquete(Paquete p){
        this.paquetesAsignados.remove(p);
        this.pesoActual -= p.getPeso();
    }

    public Camion getCamion(){
        Camion copia = new Camion(this.id, this.patente, this.estaRefrigerado, this.capacidad);
        for(Paquete p : this.paquetesAsignados){
            copia.agregarPaquete(p);
        }
        return copia;
    }

    public ArrayList<Paquete> getPaquetesAsignados(){
        return new ArrayList<>(paquetesAsignados);
    }   
}