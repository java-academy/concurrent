package cyclicbarrier.J_zadanieBariera;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import pakietpomocniczy.MyThreadFactory;

/**
 * Zadanko z bariery ;D Znowu zachęcam, aby nie zaglądać do kodu z przykładu,
 * tylko próbować samemu :) Powodzenia!
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 *
 * TODO: Napisz wyciąg na stok narciarski! Wagonik ma miejsce na 4 osoby, rusza kiedy wszyscy się
 *  załadują i tak w kółko ;)
 */
public class StokNarciarski {

  final int wielkośćWagonika;
  final int iloscNarciarzyChetnychDoWyjazdu;
  final CyclicBarrier barieraWagonika;
  ExecutorService executorService;

  public StokNarciarski(int wielkośćWagonika, int iloscNarciarzyChetnychDoWyjazdu,
      CyclicBarrier barieraWagonika) {
    this.wielkośćWagonika = wielkośćWagonika;
    this.iloscNarciarzyChetnychDoWyjazdu = iloscNarciarzyChetnychDoWyjazdu;
    this.barieraWagonika = barieraWagonika;
  }

  public static void main(String[] args) {
    int wielkśćWagonika = 4;
    CyclicBarrier baieraWagonika = new CyclicBarrier(wielkśćWagonika,
        () -> System.out.println("Wagonik dojechał!"));
    StokNarciarski stokNarciarski = new StokNarciarski(wielkśćWagonika,
        20, baieraWagonika);

    BlockingQueue<Narciarz> chętni =
        stokNarciarski.stwórzKolejkęNarciarzy(stokNarciarski.barieraWagonika);
    stokNarciarski.uruchomWszystkichNarciarzy(chętni);
  }

  BlockingQueue<Narciarz> stwórzKolejkęNarciarzy(CyclicBarrier barieraWagonika) {
    return Stream.generate(() -> new Narciarz(barieraWagonika))
        .limit(iloscNarciarzyChetnychDoWyjazdu)
        .collect(Collectors
            .toCollection(() -> new ArrayBlockingQueue<>(iloscNarciarzyChetnychDoWyjazdu)));
  }

  void uruchomWszystkichNarciarzy(BlockingQueue<Narciarz> chętni) {
    executorService = Executors
        .newFixedThreadPool(wielkośćWagonika, new MyThreadFactory("Narciarz "));

    for (int i = 0; i < iloscNarciarzyChetnychDoWyjazdu; i++) {
      try {
        executorService.submit(Objects.requireNonNull(chętni.take()));
      } catch (InterruptedException ignored) {
        System.err.println(ignored.getMessage());
      }
    }
    executorService.shutdown();
  }
}
