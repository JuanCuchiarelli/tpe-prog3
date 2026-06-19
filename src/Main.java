import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Servicios servicios = new Servicios("data/Camiones.csv", "data/Paquetes.csv");

        ArrayList<Camion> camiones = servicios.getCamiones();
        ArrayList<Paquete> paquetes = servicios.getPaquetes();

        // calcular peso total
        double pesoTotal = 0;
        for(Paquete p : paquetes){
            pesoTotal += p.getPeso();
        }

        // copia limpia de camiones para cada algoritmo
        ArrayList<Camion> camionesBacktracking = new ArrayList<>();
        for(Camion c : camiones){
            camionesBacktracking.add(c.getCamion());
        }

        ArrayList<Camion> camionesGreedy = new ArrayList<>();
        for(Camion c : camiones){
            camionesGreedy.add(c.getCamion());
        }

        // ejecutar backtracking
        Backtracking bt = new Backtracking(pesoTotal, camionesBacktracking, paquetes);
        ArrayList<Camion> solucionBT = bt.solucionBacktracking();

        // ejecutar greedy
        Greedy gr = new Greedy(camionesGreedy, paquetes);
        ArrayList<Camion> solucionGR = gr.solucionGreedy();

        // resultados backtracking
        System.out.println("=== BACKTRACKING ===");
        for(Camion c : solucionBT){
            System.out.println("Camion " + c.getId() + " (" + c.getPatente() + "): " + c.getPaquetesAsignados());
        }
        System.out.println("Peso no asignado: " + bt.getMejorPesoNoAsignado() + " kg");
        System.out.println("Estados generados: " + bt.getEstadosGenerados());

        // resultados greedy
        System.out.println("\n=== GREEDY ===");
        for(Camion c : solucionGR){
            System.out.println("Camion " + c.getId() + " (" + c.getPatente() + "): " + c.getPaquetesAsignados());
        }
        System.out.println("Peso no asignado: " + gr.getPesoNoAsignado() + " kg");
        System.out.println("Candidatos considerados: " + gr.getCandidatosConsiderados());
    }
}