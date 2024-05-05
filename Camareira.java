public class Camareira implements Runnable {
    private Hotel Pousada_dos_Suspiros;

    public Camareira(Hotel Pousada_dos_Suspiros) {
        this.Pousada_dos_Suspiros = Pousada_dos_Suspiros;
    }


    public void run() {
        while (true) {
            Pousada_dos_Suspiros.Purificarquarto();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Camareira foi interrompida.");
                return;
            }
        }
    }
}
