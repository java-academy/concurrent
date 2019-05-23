package cyclicbarrier.H_wprowadzenieBariera;

import static pakietpomocniczy.Color.*;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import pakietpomocniczy.MyThreadFactory;

/**
 * CyklicznaBariera działa bardzo podobnie do CountDownLatch, ale jest wielokrotnego użytku. Działa cyklicznie.
 * Możemy jej używać, kiedy mamy powtarzające się zadania, a któreś z nich musi poczekać na inne, zanim się rozpocznie.
 * Jeżeli zrozumiałeś mniej więcej koncept CountDownLatch - to powinno pójść gładko :)
 * W konstruktorze przekazujemy ilość zadań do zakończenia, jako drugi argument do bariery,
 * możemy przekazać jakieś zadanie (Runnable),
 * które uruchomi się za każdym razem, kiedy wykona się cykl bariery.
 *
 * @see CyclicBarrier
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 *
 * TODO:Uruchom program, przeanalizuj działanie, prześledź kod.
 *  Zastanów się, czy rozmiar egzekutora wątków jest dowolny.
 */
public class WprowadzenieBariera {

    private static final int ROZMIAR_BARIERY = 3;

    public static void main(String[] args) {
        CyclicBarrier barieraCykliczna =
                new CyclicBarrier(ROZMIAR_BARIERY, () -> {
                    System.out.printf("%sCykl się skończył, robię swoje i zaraz od nowa%s%n",
                        GREEN, RESET);
                });

        ExecutorService executorService = Executors.newFixedThreadPool(3,
            new MyThreadFactory("Zadanko Cykliczna Bariera"));
        for (int i = 0; i < 1000; i++) {
            executorService.submit(new Task(barieraCykliczna));
        }
        executorService.shutdown();
    }
}

class Task implements Runnable {
    private final CyclicBarrier barieraCykliczna;

    Task(CyclicBarrier barieraCykliczna) {
        this.barieraCykliczna = barieraCykliczna;
    }

    @Override
    public void run() {
        System.out.println("Robię swoje rzeczy, ktoś tam na mnie czeka");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
        } catch (InterruptedException ignored) {
            System.err.println(ignored.getMessage());
        }
        try {
            System.out.printf("Skończyłem %s%n", Thread.currentThread().getName());
            //TODO: Ctrl+q na metodzie await() by sprawdzić jak działa
            barieraCykliczna.await();
        } catch (InterruptedException | BrokenBarrierException ignored) {
            System.err.println(ignored.getMessage());
        }
    }
}
