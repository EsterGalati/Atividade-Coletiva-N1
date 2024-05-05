

public class Quarto {
    private int numero;
    private boolean preenchido;
    private boolean Higienizado;
    private boolean Cartoes_Rfid;

    public Quarto(int numero) {
        this.numero = numero;
        this.preenchido = false;
        this.Higienizado = true;
        this.Cartoes_Rfid = true;
    }

    public synchronized boolean acomodar_se(Hospede hospede) {
        if (!preenchido && Higienizado && Cartoes_Rfid) {
            preenchido = true;
            Higienizado = false; 
            Cartoes_Rfid = false; 
            System.out.println("Hospede " + hospede.getId() + " entrou no quarto " + numero);
            return true;
        }
        return false;
    }


    public synchronized void sair() {
        preenchido = false;
        Higienizado = false; 
        Cartoes_Rfid = true; 
        System.out.println("Quarto " + numero + " esta sem hospede e precisa ser higienizado.");
    }

    public synchronized void Higienizar() {
        if (!preenchido && Cartoes_Rfid && !Higienizado) {
            Higienizado = true;
            System.out.println("Quarto " + numero + " foi higienizado.");
        }
    }

    public boolean isOcupado() {
        return preenchido;
    }

    public boolean isLimpo() {
        return Higienizado;
    }

    public boolean isChaveNaRecepcao() {
        return Cartoes_Rfid;
    }

    public int getNumero() {
        return numero;
    }
}
