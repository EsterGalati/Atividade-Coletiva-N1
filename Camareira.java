public class Camareira implements Runnable {
    private Hotel PousadaDosSuspiros;

    public Camareira(Hotel PousadaDosSuspiros) {
        this.PousadaDosSuspiros = PousadaDosSuspiros;
    }


    public void run() {
        while (true) {
            PousadaDosSuspiros.PurificarQuarto();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Camareira foi interrompida.");
                return;
            }
        }
    }
}
