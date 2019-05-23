package countdownlatch.G_bonusik;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Różnica między CountDownLatch, a thread.join();
 * <p>
 * No to skoro już rozumiesz CountDownLatch, może zastanawiałeś się, jaka jest w sumie różnica
 * między jego działaniem, a metodą thread.join();
 * Obie te operacje gwarantują, że dalszy kod wykona się dopiero, kiedy poprzedni/poprzednie wątki skończą.
 * Hmmm... Niech no rozwieję Twoje wątpliwości!
 * Po pierwsze zauważ, że jeżeli używamy serwisu egzekutorów, to nie jesteśmy w stanie na jego wątkach
 * wywołać metody join();
 * To pierwsza ważna rzecz. Druga jest taka, że przy użyciu join CALA metoda run() musi się zakończyć, żeby
 * wątek wywołujący mógł ruszyć dalej.
 * Przy CountDownLatch mamy więcej swobody! Przypomnij sobie zadanie z pociągiem.<p/>
 * Załóżmy, że zadaniami pasażera są:
 * <p>
 * 1 - wsiąść do pociągu
 * 2 - usiąść sobie gdzieś wygodnie
 * <p>
 * Już po pierwszym zadaniu możemy wywołać metodę zatrzask.countDown();
 * <p>
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 *
 * TODO: znalezienie sobie miejsca może się odbywać, kiedy pociąg już ruszy! (spróbuj to zaimplementować w klasie Pasażer :))
 *  Odpal program kilka razy i zaobserwuj, jak to działa!
 */

public class Main {


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch zatrzask = new CountDownLatch(3);

        ExecutorService serwisEgzekutorów = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            serwisEgzekutorów.submit(new Task(zatrzask));
        }

        zatrzask.await();

        System.out.println("Zadanka skończone, ja jadę dalej");
    }
}

class Task implements Runnable {
    private final CountDownLatch zatrzask;

    Task(CountDownLatch zatrzask) {
        this.zatrzask = zatrzask;
    }

    @Override
    public void run() {
        System.out.println("Robię swoje");
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
            zatrzask.countDown();
            System.out.println("Mogę sobie dalej robić swoje a wątek główny już ruszy, ponieważ wykonałem dla niego to, " +
                    "czego potrzebował");
            Thread.sleep(3000);
            System.out.println("No i bomba :D");
        } catch (InterruptedException ignored) {
            System.err.println(ignored.getMessage());
        }
    }
}
