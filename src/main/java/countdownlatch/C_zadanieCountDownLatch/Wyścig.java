package countdownlatch.C_zadanieCountDownLatch;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;

/**
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class Wyścig implements Runnable {
  private final CountDownLatch zatrzaskCzekającyNaKierowców;
  private final CopyOnWriteArrayList<Kierowca> kierowcyGotowiDoStartu = new CopyOnWriteArrayList<>();

  Wyścig(CountDownLatch zatrzaskCzekającyNaKierowców) {
    this.zatrzaskCzekającyNaKierowców = zatrzaskCzekającyNaKierowców;
  }

  @Override
  public void run() {
    organizujWyścig();
  }

  void organizujWyścig() {
    try {
      zatrzaskCzekającyNaKierowców.await();
      rozpocznijWyścig();
    } catch (InterruptedException ignored) {
      System.err.println(ignored.getMessage());
    }
  }

  void rozpocznijWyścig() {
    System.out.println("Wszyscy są, ruszamy");
  }

  void dodajKierowcę(Kierowca kierowca) {
    kierowcyGotowiDoStartu.add(kierowca);
  }

  int getKierowcyGotowiDoStartu() {
    return kierowcyGotowiDoStartu.size();
  }

}