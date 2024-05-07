

public class Quarto {
    private int numero;
    private boolean preenchido;
    private boolean Higienizado;
    private boolean CartoesRfid;

    public Quarto(int numero) {
        this.numero = numero;
        this.preenchido = false;
        this.Higienizado = true;
        this.CartoesRfid = true;
    }

    public synchronized boolean acomodarSe(Hospede hospede) {
        if (!preenchido && Higienizado && CartoesRfid) {
            preenchido = true;
            Higienizado = false; 
            CartoesRfid = false; 
            System.out.println("Hospede " + hospede.getId() + " entrou no quarto " + numero);
            return true;
        }
        return false;
    }


    public synchronized void sair() {
        preenchido = false;
        Higienizado = false; 
        CartoesRfid = true; 
        System.out.println("Quarto " + numero + " esta sem hospede e precisa ser higienizado.");
    }

    public synchronized void Higienizar() {
        if (!preenchido && CartoesRfid && !Higienizado) {
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
        return CartoesRfid;
    }

    public int getNumero() {
        return numero;
    }
}
