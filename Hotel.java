


import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Hotel {
    private final int Numero_de_quartos;
    private final ConcurrentHashMap<Integer, Quarto> suit;
    private final BlockingQueue<Hospede> filaEspera;
    private final ReentrantLock lock;

    public Hotel(int Numero_de_quartos) {
        this.Numero_de_quartos = Numero_de_quartos;
        this.suit = new ConcurrentHashMap<>();
        this.filaEspera = new LinkedBlockingQueue<>();
        this.lock = new ReentrantLock();


        for (int i = 1; i <= Numero_de_quartos; i++) {
            suit.put(i, new Quarto(i));
        }
        System.out.println("\n\nHotel com " + Numero_de_quartos + " quarto(s) disponivel(eis) e pronto para uso\n        \n    ________________________\r\n" + //
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
                    entry.getValue().acomodar_se(hospede);
                    return entry.getKey(); 
                }
            }
            return 0; 
        } finally {
            lock.unlock();
        }
    }

    public void Reservar_quarto() {
        lock.lock();
        try {
            while (!filaEspera.isEmpty()) {
                Hospede hospede = filaEspera.peek();
                for (Quarto suit : suit.values()) {
                    if (!suit.isOcupado() && suit.isLimpo() && suit.isChaveNaRecepcao()) {
                        if (suit.acomodar_se(hospede)) {
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

    public void Purificarquarto() {
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

    public void checkOut(int qtd_Quartos) {
        lock.lock();
        try {
            Quarto quarto = suit.get(qtd_Quartos);
            if (quarto != null && quarto.isOcupado()) {
                quarto.sair();
                System.out.println("Hospede no quarto " + qtd_Quartos + " fez check-out. ");
                Reservar_quarto(); 
            }
        } finally {
            lock.unlock();
        }
    }

    public int getTotalRooms() {
        return Numero_de_quartos;
    }

    public boolean areAllRoomsOccupied() {
        return suit.size() == Numero_de_quartos && suit.values().stream().allMatch(Quarto::isOcupado);
    }

    public static void main(String[] args) {
        Hotel Pousada_dos_Suspiros = new Hotel(10); 

        for (int i = 0; i < 50; i++) {
            new Thread(new Hospede(Pousada_dos_Suspiros, 1)).start(); 
        }
        for (int j = 0; j < 10; j++) {
            new Thread(new Camareira(Pousada_dos_Suspiros)).start(); 
        }
        for (int k = 0; k < 5; k++) {
            new Thread(new Recepcionista(Pousada_dos_Suspiros)).start(); 
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