package Zad_4;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Przeczytaj i zastanów się co robi ten program i dlaczego nie działa poprawnie.
 * Spraw żeby zaczął :)
 * @author Jakub Czajka
 */
class Zad_4 {

  public static void main(String[] args) throws InterruptedException {
    final List<Integer> list = new CopyOnWriteArrayList<>();
    List<Callable<Object>> collect = Stream.generate(() -> new Task(list)).limit(10)
        .map(Executors::callable).collect(Collectors.toList());
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    executorService.invokeAll(collect);
    Collections.sort(list);
    System.out.println("List size :" + list.size());
    System.out.println("List :" + list);
  }
}

class Task implements Runnable {

  List<Integer> list;

  Task(List<Integer> list) {
    this.list = list;
  }

  @Override
  public void run() {
    for (int i = 0; i < 100; i++) {
      if (!list.contains(i)) {
        list.add(i);
      }
    }
  }
}


