package countdownlatch.B_przykładCountdownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Zadaniem każdego pasażera jest wsiąść do pociągu.
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class Pasażer implements Runnable{
    private final Pociąg pociąg;
    private final CountDownLatch zatrzaskCzekającyNaKompletPasażerów;

    Pasażer(Pociąg pociąg, CountDownLatch zatrzaskCzekającyNaKompletPasażerów) {
        this.pociąg = pociąg;
        this.zatrzaskCzekającyNaKompletPasażerów = zatrzaskCzekającyNaKompletPasażerów;
    }

    @Override
    public void run() {
        System.out.println("O cholera! Lece na pociąg!");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(10000));
            pociąg.pasażerStawiłSięNaPociąg(this);
            zatrzaskCzekającyNaKompletPasażerów.countDown();
        } catch (InterruptedException ignored) {
            System.err.println(ignored.getMessage());
        }
    }
    /**
     * @return nazwa wątku zależna od nazwy fabryki wątków.
     * @see pakietpomocniczy.MyThreadFactory
     */
    @Override
    public String toString() {
        return Thread.currentThread().getName();
    }
}
