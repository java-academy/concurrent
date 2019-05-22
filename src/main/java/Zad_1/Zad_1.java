package Zad_1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Przeczytaj i zastanów się co robi ten program
 * Uruchom 5 razy, popraw klasę tak aby przestała wyrzucać NPE i lista miała wielkość równą ilości
 * zadań;
 *
 * @author Jakub Czajka
 */
class Zad_1 {

  final static int NUMBER_OF_TASKS = 100;

  public static void main(String[] args) throws InterruptedException {
    final OddEvenList oddEvenList = new OddEvenList();
    List<Runnable> collect = Stream.generate(() -> (Runnable) () -> {
          oddEvenList.addNumber(ThreadLocalRandom.current().nextInt(1, 100));
        }
    ).limit(NUMBER_OF_TASKS).collect(Collectors.toList());

    List<Callable<Object>> collect1 = collect.stream().map(Executors::callable)
        .collect(Collectors.toList());
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    executorService.invokeAll(collect1);
    executorService.shutdown();
    for (Integer integer : oddEvenList.getList()) {
      System.out.println(integer);
    }
    System.out.printf("Odd numbers: %d  Even numbers %d, Size %d", oddEvenList.getOddsNumbers(),
        oddEvenList.getEvenNumbers(), oddEvenList.getList().size());

  }

}

class OddEvenList {

  List<Integer> list = new ArrayList<>();
  int oddsNumbers = 0;
  int evenNumbers = 0;

  void addNumber(int i) {
    list.add(i);
    updateCounter(i);
  }

  void updateCounter(int i) {
    if (i % 2 == 0) {
      evenNumbers++;
    } else {
      oddsNumbers++;
    }
  }

  public int getOddsNumbers() {
    return oddsNumbers;
  }

  public int getEvenNumbers() {
    return evenNumbers;
  }

  List<Integer> getList() {
    return list;
  }
}