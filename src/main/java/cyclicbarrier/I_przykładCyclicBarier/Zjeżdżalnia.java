package cyclicbarrier.I_przykładCyclicBarier;

import static pakietpomocniczy.Color.*;

import java.util.concurrent.CyclicBarrier;

/**
 * Nasza zjeżdżalnia służy do wyścigów. Mamy x pozycji do startu, dlatego nasza bariera
 * będzie oczekiwać na x gotowych chętnych. Dopiero wtedy osoba pilnująca zjeżdżali
 * da sygnał do startu.
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class Zjeżdżalnia implements Runnable {
    private final int liczbaTorów;
    private CyclicBarrier barrierZjeżdzalnii;

    Zjeżdżalnia(int liczbaTorów) {
        this.liczbaTorów = liczbaTorów;
        this.barrierZjeżdzalnii = new CyclicBarrier(liczbaTorów, this);
    }

    @Override
    public void run() {
        System.out.printf("%sMamy %d chętych, odjeżdżamy!%s%n", GREEN, liczbaTorów, RESET);
    }

    CyclicBarrier getBarrier() {
        return barrierZjeżdzalnii;
    }
}
