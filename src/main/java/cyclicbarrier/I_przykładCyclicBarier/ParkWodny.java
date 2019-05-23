package cyclicbarrier.I_przykładCyclicBarier;

import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import pakietpomocniczy.MyThreadFactory;

/**
 * Przykładowe wykorzystanie Cyklicznej Bariery.
 * Wyobraźmy sobie park wodny. Mamy zjeżdżalnię, która ma równoległe 4 tory - do wyścigów. Dopiero kiedy
 * cztery osoby z kolejki ustawią się na pozycję, dostaną zielone światło. Zjadą, a do zjeżdżalni podejdą,
 * kolejne cztery osoby i tak w kółko. W tym celu - cyklicznej operacji, która czeka na inne (4 osoby przy starcie
 * z których każda może się ustawić w nieco innym czasie), możemy zastosować klasę CyclicBarrier.
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 *
 * TODO: Odpal program, przeglądnij kod, zrozum koncept :)
 */
public class ParkWodny {

    private static final int ILOSC_CHETNYCH_NA_ZJEZDZALNIE = 20;

    public static void main(String[] args) {

        Zjeżdżalnia zjeżdżalnia = new Zjeżdżalnia(4);

        CyclicBarrier barrierZjeżdzalnii = zjeżdżalnia.getBarrier();

        /*
         * Tworzymy kolejkę 20 chętnych na zjeżdżalnię, każdy z nich otrzymuje referencję do naszej bariery.
         * Przy okazji fajny sposób na stworzenie kolejki z użyciem streamów :)
         */
        BlockingQueue<ChętnyNaZjeżdżalnię> chętni =
            Stream.generate(() -> new ChętnyNaZjeżdżalnię(barrierZjeżdzalnii))
                .limit(ILOSC_CHETNYCH_NA_ZJEZDZALNIE)
                .collect(Collectors.toCollection(() ->
                    new ArrayBlockingQueue<>(ILOSC_CHETNYCH_NA_ZJEZDZALNIE)));

        ExecutorService executorService = Executors.newFixedThreadPool(4,
            new MyThreadFactory("Zjeżdżający "));

        for (int i = 0; i < ILOSC_CHETNYCH_NA_ZJEZDZALNIE; i++) {
            try {
                executorService.submit(Objects.requireNonNull(chętni.take()));
            } catch (InterruptedException ignored) {
                System.err.println(ignored.getMessage());
            }
        }
        executorService.shutdown();
    }
}