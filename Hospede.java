import java.util.ArrayList;
import java.util.List;

public class Hospede implements Runnable {
    private static int rastrear_identificador = 1; 
    private final int Identificador;
    private final Hotel Pousada_dos_Suspiros;
    private final int groupSize; 
    private int attempts = 0; 
    private List<Integer> qtd_Quartos_Alocados; 

    private static synchronized int getRastrear_identificador() {
        return rastrear_identificador++;
    }

    public Hospede(Hotel Pousada_dos_Suspiros, int groupSize) {
        this.Pousada_dos_Suspiros = Pousada_dos_Suspiros;
        this.groupSize = groupSize;
        this.Identificador = getRastrear_identificador(); 
        this.qtd_Quartos_Alocados = new ArrayList<>(); 
    }


    public int getId() {
        return Identificador;
    }

    
    public void run() {
        int solicitacao_Quarto = (groupSize + 3) / 4; 
        int Quartos_Alocados = 0;

        while (Quartos_Alocados < solicitacao_Quarto && attempts < 2) {
            int bookedRoom = Pousada_dos_Suspiros.tentarReservar(this);
            if (bookedRoom > 0) {
                Quartos_Alocados++;
                qtd_Quartos_Alocados.add(bookedRoom); 
                System.out.println("Hospede " + Identificador + " entrou e reservou quarto numero " + bookedRoom
                        + ". Total reservado: " + Quartos_Alocados);
                if (Quartos_Alocados == solicitacao_Quarto) {
                    System.out.println("Hospede " + Identificador + " reservou todos os quartos necessarios.");
                    simulateStay(); 
                }
            } else {
                attempts++;
                if (attempts >= 2) {
                    System.out.println("Hospede " + Identificador + " nao conseguiu reservar todos os quartos necessarios apos " +
                            attempts + " tentativas e deixa uma reclamacao.");
                    return; 
                }
                try {
                    System.out.println("Hospede " + Identificador
                            + " nao conseguiu reservar um quarto. Vai passear pela cidade e mais tarde tente novamente.");
                    Thread.sleep(10000); 
                } catch (InterruptedException e) {
                    System.out.println("Hospede " + Identificador + " foi interrompido e vai embora.");
                    return;
                }
            }
        }
    }

    private void simulateStay() {
        try {
            int stayDuration = 5000; 
            Thread.sleep(stayDuration);
            qtd_Quartos_Alocados.forEach(qtd_Quartos -> Pousada_dos_Suspiros.checkOut(qtd_Quartos)); 
        } catch (InterruptedException e) {
            System.out.println("Hospede " + Identificador + " foi interrompido durante sua estadia e vai embora mais cedo.");
        }
    }
}
