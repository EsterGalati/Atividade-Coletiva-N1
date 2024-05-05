
public class Recepcionista implements Runnable {
    private Hotel Pousada_dos_Suspiros;

    public Recepcionista(Hotel Pousada_dos_Suspiros) {
        this.Pousada_dos_Suspiros = Pousada_dos_Suspiros;
    }

    public void run() {
        while (true) {
            Pousada_dos_Suspiros.Reservar_quarto();
        }
    }
}
