package countdownlatch.D_zadanieCountDownLatch;

import static pakietpomocniczy.Color.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class WielkiTest implements Runnable {

  final CountDownLatch zatrzaskDoRozpoczęciaTestu;
  final CountDownLatch zatrzaskDoOgłoszeniaWynikówJakWszyscySkończąTest;

  WielkiTest(CountDownLatch zatrzaskDoRozpoczęciaTestu,
      CountDownLatch zatrzaskDoOgłoszeniaWynikówJakWszyscySkończąTest) {
    this.zatrzaskDoRozpoczęciaTestu = zatrzaskDoRozpoczęciaTestu;
    this.zatrzaskDoOgłoszeniaWynikówJakWszyscySkończąTest = zatrzaskDoOgłoszeniaWynikówJakWszyscySkończąTest;
  }

  @Override
  public void run() {
    try {
      System.out.println(GREEN+"Witamy na wielkim teście z historii!"+RESET);
      zatrzaskDoRozpoczęciaTestu.await();
      System.out.println(GREEN+"Wszyscy gotowi, rozpoczynamy test!"+RESET);
    } catch (InterruptedException ignored) {
      System.err.println(ignored.getMessage());
    }
    try {
      zatrzaskDoOgłoszeniaWynikówJakWszyscySkończąTest.await();
      System.out.println(GREEN+"Wszyscy skończyli pisać test! "
          + "Gratulujemy, ogłaszamy wyniki!"+RESET);
    } catch (InterruptedException ignored) {
      System.err.println(ignored.getMessage());
    }
  }
}