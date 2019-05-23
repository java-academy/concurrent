package cyclicbarrier.J_zadanieBariera;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Kacper Staszek
 * @author Marcin Ogorzalek
 */
class Narciarz implements Runnable {

  private final CyclicBarrier baieraWagonika;

  Narciarz(CyclicBarrier baieraWagonika) {
    this.baieraWagonika = baieraWagonika;
  }

  @Override
  public void run() {
    System.out.println("Idę do wagonika " + this);
    try {
      Thread.sleep(ThreadLocalRandom.current().nextInt(2000));
      wsiadamDoWagonika();
    } catch (InterruptedException | BrokenBarrierException ignore) {
      System.err.println(ignore.getMessage());
    }
  }

  int wsiadamDoWagonika() throws InterruptedException, BrokenBarrierException {
    System.out.println("Ok, jestem gotów do wyjazdu! " + this);
    System.out.println("W wagoniku siedzi już "
        + (baieraWagonika.getNumberWaiting() + 1) + " narciarzy");
    return baieraWagonika.await();
  }

  @Override
  public String toString() {
    return Thread.currentThread().getName();
  }
}