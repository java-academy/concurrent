package countdownlatch.F_zadanieCountDownLatchTimeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class CzłonekEkipy implements Runnable {

  private final CountDownLatch zatrzaskKierowcy;
  private final Więzienie więzienie;
  private final int maksymalnyCzasRoboty;

  CzłonekEkipy(CountDownLatch zatrzaskKierowcy, Więzienie więzienie, int maksymalnyCzasRoboty) {
    this.zatrzaskKierowcy = zatrzaskKierowcy;
    this.więzienie = więzienie;
    this.maksymalnyCzasRoboty = maksymalnyCzasRoboty;
  }

  @Override
  public void run() {
    kradzież();
  }

  void kradzież() {
    System.out.printf("%s Chwila, zagarniam jeszcze łupy!!%n", Thread.currentThread().getName());
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(maksymalnyCzasRoboty));
      czasUciekać();
    } catch (InterruptedException e) {
      System.err.printf("%s Dostałem kulkę! Już po mnie%n", Thread.currentThread().getName());
      więzienie.złapZłodzieja(this);
    }
  }

  void czasUciekać() {
    System.out.println(Thread.currentThread().getName() + " Mam wszystko, czas się zwijać.");
    zatrzaskKierowcy.countDown();
  }
}