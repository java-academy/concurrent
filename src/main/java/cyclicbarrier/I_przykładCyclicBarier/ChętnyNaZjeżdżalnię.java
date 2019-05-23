package cyclicbarrier.I_przykładCyclicBarier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Zadaniem chętnego jest podejść do stanowiska i zająć miejsce
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class ChętnyNaZjeżdżalnię implements Runnable {

    private final CyclicBarrier barrierZjeżdzalnii;

    ChętnyNaZjeżdżalnię(CyclicBarrier barrierZjeżdzalnii) {
        this.barrierZjeżdzalnii = barrierZjeżdzalnii;
    }

    @Override
    public void run() {
        try {
            System.out.println("Podchodzę do stanowiska " + this);
            Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
            System.out.println("Ok, jestem gotowy! " + this);
            barrierZjeżdzalnii.await();
        } catch (InterruptedException | BrokenBarrierException ignored) {
            System.err.println(ignored.getMessage());
        }

    }

    /**
     * @return zależy nazwy fabryki.
     * @see pakietpomocniczy.MyThreadFactory
     */
    @Override
    public String toString() {
        return Thread.currentThread().getName();
    }
}
