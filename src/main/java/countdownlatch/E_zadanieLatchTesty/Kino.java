package countdownlatch.E_zadanieLatchTesty;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */

class Kino implements Runnable {
  private final CountDownLatch zatrzaskCzekającyZRozpoczęciemSeansu;
  List<KlientKina> klienciZRezerwacją = new ArrayList<>();
  Lock zamek = new ReentrantLock();

  Kino(CountDownLatch zatrzaskCzekającyZRozpoczęciemSeansu) {
    this.zatrzaskCzekającyZRozpoczęciemSeansu = zatrzaskCzekającyZRozpoczęciemSeansu;
  }

  @Override
  public void run() {
    try {
      zatrzaskCzekającyZRozpoczęciemSeansu.await();
      System.out.println("Startujemy seans!");
    } catch (InterruptedException ignored) {
      System.err.println(ignored.getMessage());
    }
  }

  void rezerwujBilet(KlientKina klientKina, int ileCzasuKlientZajmujeBilet)
      throws InterruptedException {
    try {
      zamek.lock();
      System.out.println("Ktoś rezerwuje u nas bilecik.");
      Thread.sleep(ileCzasuKlientZajmujeBilet);
      klienciZRezerwacją.add(klientKina);
      if(zatrzaskCzekającyZRozpoczęciemSeansu.getCount()>1) {
        System.out.println("Bilecik zarezerwowany, zostało jeszcze " + (
            zatrzaskCzekającyZRozpoczęciemSeansu.getCount()-1));
      } else {
        System.out.println("Ostatni bilet zarezerwowany!");
      }
    } finally {
      zamek.unlock();
    }
  }
}
