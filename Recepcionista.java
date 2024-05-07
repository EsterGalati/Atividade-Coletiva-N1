
public class Recepcionista implements Runnable {
    private Hotel PousadaDosSuspiros;

    public Recepcionista(Hotel PousadaDosSuspiros) {
        this.PousadaDosSuspiros = PousadaDosSuspiros;
    }

    public void run() {
        while (true) {
            PousadaDosSuspiros.ReservarQuarto();
        }
    }
}
