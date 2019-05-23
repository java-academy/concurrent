package countdownlatch.B_przykładCountdownLatch;

import pakietpomocniczy.MyThreadFactory;

import java.util.concurrent.*;

/**
 * <p>Przykładowe zadanko - problem polega na tym, że mamy pociąg do Hogwartu ;D Wyobraźmy sobie, że kilku czarodziejów
 * właśnie otrzymało list, więc lecą ile sił w nogach na pociąg.
 * Konduktor jest na tyle spoko, że zaczeka na wszystkich, zanim ruszy.</p>
 *
 * @see Pociąg
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 *
 * TODO:Uruchom program kilka razy, prześledź działanie.
 * TODO:Zakomentuj oznaczoną linię w klasie Pociąg, uruchom program i zrozum działanie metody await();
 * TODO: Zastanów się, czy egzekutor może mieć dowolny rozmiar?
 */
public class PodróżDoHogwardu {

    private static final int ILOSC_ZAREZERWOWANYCH_BILETOW = 10;

    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<Pasażer> pasażerowie = new LinkedBlockingQueue<>();

        Pociąg pociąg = new Pociąg(ILOSC_ZAREZERWOWANYCH_BILETOW, pasażerowie);

        CountDownLatch zatrzaskPociągu = pociąg.getCountDownLatch();

        ExecutorService serwisEgzekutorów = Executors.newFixedThreadPool(10, new MyThreadFactory("Pasażer"));

        for (int i = 0; i < ILOSC_ZAREZERWOWANYCH_BILETOW; i++) {
            serwisEgzekutorów.submit(new Pasażer(pociąg, zatrzaskPociągu));
        }
        serwisEgzekutorów.shutdown();

        pociąg.pociągOdjeżdża();
    }
}

