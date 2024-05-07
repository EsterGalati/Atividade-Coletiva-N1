


import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Hotel {
    private final int NumeroDeQuartos;
    private final ConcurrentHashMap<Integer, Quarto> suit;
    private final BlockingQueue<Hospede> filaEspera;
    private final ReentrantLock lock;

    public Hotel(int NumeroDeQuartos) {
        this.NumeroDeQuartos = NumeroDeQuartos;
        this.suit = new ConcurrentHashMap<>();
        this.filaEspera = new LinkedBlockingQueue<>();
        this.lock = new ReentrantLock();


        for (int i = 1; i <= NumeroDeQuartos; i++) {
            suit.put(i, new Quarto(i));
        }
        System.out.println("\n\nHotel com " + NumeroDeQuartos + " quarto(s) disponivel(eis) e pronto para uso\n        \n    ________________________\r\n" + //
                        "    | Pousada Dos Suspiro  |\r\n" + 
                        "    |----------------------|\r\n" + 
                        "    |---- X X X X X X -----|\r\n" + 
                        "    |----------------------|\r\n" + 
                        "    |   (*v*)_/\\_(*v*)     | \n\n\n");
    }

    public int tentarReservar(Hospede hospede) {
        lock.lock();
        try {
            for (Map.Entry<Integer, Quarto> entry : suit.entrySet()) {
                if (!entry.getValue().isOcupado() && entry.getValue().isLimpo()
                        && entry.getValue().isChaveNaRecepcao()) {
                    entry.getValue().acomodarSe(hospede);
                    return entry.getKey(); 
                }
            }
            return 0; 
        } finally {
            lock.unlock();
        }
    }

    public void ReservarQuarto() {
        lock.lock();
        try {
            while (!filaEspera.isEmpty()) {
                Hospede hospede = filaEspera.peek();
                for (Quarto suit : suit.values()) {
                    if (!suit.isOcupado() && suit.isLimpo() && suit.isChaveNaRecepcao()) {
                        if (suit.acomodarSe(hospede)) {
                            System.out.println("Hospede " + hospede.getId() + " instalado no quarto " + suit.getNumero() + " da fila de espera.");
                            filaEspera.remove(hospede);
                            break;
                        }
                    }
                }
                break;
            }
        } finally {
            lock.unlock();
        }
    }

    public void PurificarQuarto() {
        lock.lock();
        try {
            for (Quarto quarto : suit.values()) {
                if (quarto.isChaveNaRecepcao() && !quarto.isOcupado() && !quarto.isLimpo()) {
                    quarto.Higienizar(); 
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public void checkOut(int qtdQuartos) {
        lock.lock();
        try {
            Quarto quarto = suit.get(qtdQuartos);
            if (quarto != null && quarto.isOcupado()) {
                quarto.sair();
                System.out.println("Hospede no quarto " + qtdQuartos + " fez check-out. ");
                ReservarQuarto(); 
            }
        } finally {
            lock.unlock();
        }
    }

    public int getTotalRooms() {
        return NumeroDeQuartos;
    }

    public boolean areAllRoomsOccupied() {
        return suit.size() == NumeroDeQuartos && suit.values().stream().allMatch(Quarto::isOcupado);
    }

    public static void main(String[] args) {
        Hotel PousadaDosSuspiros = new Hotel(10); 

        for (int i = 0; i < 50; i++) {
            new Thread(new Hospede(PousadaDosSuspiros, 1)).start(); 
        }
        for (int j = 0; j < 10; j++) {
            new Thread(new Camareira(PousadaDosSuspiros)).start(); 
        }
        for (int k = 0; k < 5; k++) {
            new Thread(new Recepcionista(PousadaDosSuspiros)).start(); 
        }
    }
}












/*  _______________________
    | Pousada Dos Suspiro  |
    |￣￣￣￣￣￣￣￣￣￣￣￣|
    |￣￣￣★★★★★★￣￣￣|
    |￣￣￣￣￣￣￣￣￣￣￣￣|
    |(★‿★)_/\_(✿◕‿◕✿) | 
 
 */