package countdownlatch.C_zadanieCountDownLatch;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @see countdownlatch.B_przykładCountdownLatch.Pasażer
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class Kierowca implements Runnable {
  private final CountDownLatch zatrzaskCzekającyNaKierowców;
  private final Wyścig wyścig;

  Kierowca(CountDownLatch zatrzaskCzekającyNaKierowców, Wyścig wyścig) {
    this.zatrzaskCzekającyNaKierowców = zatrzaskCzekającyNaKierowców;
    this.wyścig = wyścig;
  }

  @Override
  public void run() {
    System.out.println("Dostałem sms o wyścigu. Wyruszam na start "
        + Thread.currentThread().getName());
    jadęNaLinięStartu();
    dojechałemNaLinięStartu();
  }

  void jadęNaLinięStartu() {
    System.out.println("Jadę na start " + Thread.currentThread().getName());
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(1000,2000));
    } catch (InterruptedException e) {
      System.err.println("Zepsuło mi się auto " + Thread.currentThread().getName());
    }
  }

  void dojechałemNaLinięStartu() {
    System.out.println("Dojechałem na start i jestem gotowy "
        + Thread.currentThread().getName());
    zatrzaskCzekającyNaKierowców.countDown();
    wyścig.dodajKierowcę(this);
  }
}