package countdownlatch.E_zadanieLatchTesty;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class KlientKina implements Runnable{

  private final Kino kino;
  private final CountDownLatch zatrzaskCzekającyZRozpoczęciemSeansu;

  KlientKina(Kino kino, CountDownLatch zatrzaskCzekającyZRozpoczęciemSeansu) {
    this.kino = kino;
    this.zatrzaskCzekającyZRozpoczęciemSeansu = zatrzaskCzekającyZRozpoczęciemSeansu;
  }

  @Override
  public void run() {
    System.out.println("Ale sobie zarezerwuje bilecik!!");
    try {
      kino.rezerwujBilet(this, ThreadLocalRandom.current().nextInt(2000));
      zatrzaskCzekającyZRozpoczęciemSeansu.countDown();
    } catch (InterruptedException ignored) {
      System.err.println(ignored.getMessage());
    }
  }
}
