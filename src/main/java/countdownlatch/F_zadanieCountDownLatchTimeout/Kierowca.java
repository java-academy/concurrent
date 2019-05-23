package countdownlatch.F_zadanieCountDownLatchTimeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class Kierowca implements Runnable {

  private final CountDownLatch zatrzaskKierowcy;
  private final ExecutorService ekipaExecutor;
  private final TimeUnit timeUnit = TimeUnit.MILLISECONDS;
  private final long czasDoPrzyjazduPolicji;

  Kierowca(CountDownLatch zatrzaskKierowcy, ExecutorService ekipaExecutor,
      long czasDoPrzyjazduPolicji) {
    this.zatrzaskKierowcy = zatrzaskKierowcy;
    this.ekipaExecutor = ekipaExecutor;
    this.czasDoPrzyjazduPolicji = czasDoPrzyjazduPolicji;
  }

  @Override
  public void run() {
    System.out.println("Szybko chłopaki, do samochodu. Policja jest w drodze");
    ucieczka();
  }

  void ucieczka() {
    try {
      zatrzaskKierowcy.await(czasDoPrzyjazduPolicji, timeUnit);
      ekipaExecutor.shutdownNow();
      System.out.println("No najwyższy czas");
    } catch (InterruptedException e) {
      System.err.println("Dopadli nas!");
    }
  }
}

