package countdownlatch.E_zadanieLatchTesty;

import pakietpomocniczy.MyThreadFactory;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Klasa symuluje rezerwowanie biletów do kina.
 * Mamy tutaj sekcję krytyczną, oraz zatrzask, który czeka na wyprzedanie
 * biletów.
 * TODO:Zaimplementuj odpowiednie testy do tej klasy, zgodne z sygnaturami
 *  metod testowych.
 *  Zacznij od testowania z użyciem Awaitility, następnie napisz testy bez
 *  wykorzystania tej biblioteki.
 *
 * @see CountDownLatch
 * @see java.util.concurrent.locks.ReentrantLock
 * @see <a href="https://github.com/awaitility/awaitility/wiki/Usage">Awaitility</a>
 * @see <a href="https://testng.org/doc/documentation-main.html">TestNG</a>
 *
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
class RezerwacjaBiletówDoKina {
  private static final int LICZBA_DOSTĘPNYCH_BIETÓW = 5;

  private final int liczbaBiletów;

  RezerwacjaBiletówDoKina(int liczbaBiletów) {
    this.liczbaBiletów = liczbaBiletów;
  }

  public static void main(String[] args) {
    RezerwacjaBiletówDoKina rezerwacjaBiletówDoKina =
        new RezerwacjaBiletówDoKina(LICZBA_DOSTĘPNYCH_BIETÓW);
    MetadaneRezerwacji metadaneKina = rezerwacjaBiletówDoKina.stworzeniePotrzebnychObiektów();
    rezerwacjaBiletówDoKina.uruchomienieRezerwacji(metadaneKina);
  }

  MetadaneRezerwacji stworzeniePotrzebnychObiektów() {
    CountDownLatch zatrzaskCzekającyZRozpoczęciemSeansu = new CountDownLatch(liczbaBiletów);
    Kino kino = new Kino(zatrzaskCzekającyZRozpoczęciemSeansu);
    ExecutorService serwisEgzekutorów = Executors.newFixedThreadPool(liczbaBiletów,
        new MyThreadFactory("Klient"));

    return new MetadaneRezerwacji(zatrzaskCzekającyZRozpoczęciemSeansu, kino, serwisEgzekutorów);
  }

  void uruchomienieRezerwacji(MetadaneRezerwacji metadaneKina) {
    new Thread(metadaneKina.kino,"Kino").start();
    for (int i = 0; i < liczbaBiletów; i++) {
      metadaneKina.service.submit(new KlientKina(metadaneKina.kino, metadaneKina.zatrzaskCzekającyZRozpoczęciemSeansu));
    }
    metadaneKina.service.shutdown();
  }

  class MetadaneRezerwacji {
    CountDownLatch zatrzaskCzekającyZRozpoczęciemSeansu;
    Kino kino;
    ExecutorService service;

    MetadaneRezerwacji(CountDownLatch zatrzaskCzekającyZRozpoczęciemSeansu, Kino kino, ExecutorService serwisEgzekutorów) {
      this.zatrzaskCzekającyZRozpoczęciemSeansu = zatrzaskCzekającyZRozpoczęciemSeansu;
      this.kino = kino;
      this.service = serwisEgzekutorów;
    }
  }
}


