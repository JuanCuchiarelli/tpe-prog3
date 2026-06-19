public class Paquete {
    
    private int id;
    private String codigo;
    private double peso;
    private boolean contieneAlimento;
    private int nivelUrgencia;

    public Paquete(int id, String codigo, double peso, boolean contieneAlimento, int nivelUrgencia){
        this.id = id;
        this.codigo = codigo;
        this.peso = peso;
        this.contieneAlimento = contieneAlimento;
        this.nivelUrgencia = nivelUrgencia;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPeso() {
        return peso;
    }


    public boolean getContieneAlimento() {
        return contieneAlimento;
    }

    public int getNivelUrgencia() {

        return nivelUrgencia;
    }

    @Override
    public String toString(){
        return codigo + " (" + peso + "kg)";
    }
}
