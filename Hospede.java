import java.util.ArrayList;
import java.util.List;

public class Hospede implements Runnable {
    private static int rastrearIdentificador = 1; 
    private final int Identificador;
    private final Hotel PousadaDosSuspiros;
    private final int groupSize; 
    private int attempts = 0; 
    private List<Integer> qtdQuartosAlocados; 

    private static synchronized int getrastrearIdentificador() {
        return rastrearIdentificador++;
    }

    public Hospede(Hotel PousadaDosSuspiros, int groupSize) {
        this.PousadaDosSuspiros = PousadaDosSuspiros;
        this.groupSize = groupSize;
        this.Identificador = getrastrearIdentificador(); 
        this.qtdQuartosAlocados = new ArrayList<>(); 
    }


    public int getId() {
        return Identificador;
    }

    
    public void run() {
        int solicitacaoQuarto = (groupSize + 3) / 4; 
        int quartosAlocados = 0;

        while (quartosAlocados < solicitacaoQuarto && attempts < 2) {
            int bookedRoom = PousadaDosSuspiros.tentarReservar(this);
            if (bookedRoom > 0) {
                quartosAlocados++;
                qtdQuartosAlocados.add(bookedRoom); 
                System.out.println("Hospede " + Identificador + " entrou e reservou quarto numero " + bookedRoom
                        + ". Total reservado: " + quartosAlocados);
                if (quartosAlocados == solicitacaoQuarto) {
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
            qtdQuartosAlocados.forEach(qtdQuartos -> PousadaDosSuspiros.checkOut(qtdQuartos)); 
        } catch (InterruptedException e) {
            System.out.println("Hospede " + Identificador + " foi interrompido durante sua estadia e vai embora mais cedo.");
        }
    }
}
