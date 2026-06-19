import java.util.ArrayList;


public class Backtracking{

    private ArrayList<Camion> camiones;
    private ArrayList<Paquete> paquetes;
    private double pesoTotal;
    private double mejorPesoNoAsignado;
    private ArrayList<Camion> mejorSolucion;
    private int estadosGenerados;

    //preguntar si pasamos por parametros los arrays
    public Backtracking(double pesoTotal, ArrayList<Camion> camiones, ArrayList<Paquete> paquetes){
        this.pesoTotal = pesoTotal;
        this.camiones = camiones;
        this.paquetes = paquetes;
        this.mejorSolucion = new ArrayList<>();
        this.mejorPesoNoAsignado = pesoTotal;
        this.estadosGenerados = 0;
    }
    /*
    *SOLUCION OBTENIDA: 
    * Estrategia: se exploran todas las combinaciones posibles de asignación de paquetes a camiones.
    * Para cada paquete se prueban dos opciones: asignarlo a cada camión válido (que tenga capacidad
    * y refrigeración si el paquete contiene alimentos), o no asignarlo. Cuando se procesan todos los
    * paquetes se calcula el peso no asignado y se actualiza la mejor solución si es menor a la anterior.
    * La complejidad es O((c+1)^n) donde c es la cantidad de camiones y n la de paquetes.
    */
    public ArrayList<Camion> solucionBacktracking(){
        ArrayList<Camion>solucionActual = new ArrayList<>();
        back(0, solucionActual);
        return mejorSolucion;
   }

    private void back(int posicion, ArrayList<Camion>solucionActual){   
    estadosGenerados++;
    //si hay solucion
    if(posicion == paquetes.size()){
        double pesoAsignado = 0;
        for(Camion c : camiones){
            pesoAsignado += c.getPesoActual();
        }
        double pesoNoAsignado = pesoTotal - pesoAsignado;
        
        if(pesoNoAsignado < mejorPesoNoAsignado){
            mejorSolucion.clear();
            mejorPesoNoAsignado = pesoNoAsignado;
            for(Camion c : camiones){
                mejorSolucion.add(c.getCamion());
            }
        }
        return;
    
    }
    else{
        Paquete pack = paquetes.get(posicion);
        for(Camion c : camiones){
            if(c.puedeAsignar(pack)){
                c.agregarPaquete(pack);
                solucionActual.add(c);
                back(posicion + 1, solucionActual);
                c.quitarPaquete(pack);
                solucionActual.remove(solucionActual.size()-1);
                }  
            }
            //se usa para explorar la opcionde no asignar el paquete actual a un camion y seguir con el resto
           back(posicion + 1, solucionActual);   
        }
    }
   
    public double getMejorPesoNoAsignado(){
        return mejorPesoNoAsignado;
    }

    public int getEstadosGenerados(){
        return estadosGenerados;
    }
}

