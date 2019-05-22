package Zad_2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Przeczytaj i zastanów się co robi ten program.
 * Uruchom 5 razy spraw żeby za każdym razem wynik był na zielono nie licząc żółtego pivota
 * @author Jakub Czajka
 */
class Zad_2 {
  static int PIVOT = -1;
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";

  public static void main(String[] args) throws InterruptedException {
    Object lock = new Object();
    final Deque<Integer> deque = new LinkedList<>();
    deque.addFirst(PIVOT);
    List<Runnable> runnableLis = Stream.generate(() -> (Runnable) () -> {
      if (deque.size() % 2 == 0) {
        synchronized (lock) {
          deque.addFirst(deque.size());
        }
      } else {
        synchronized (lock) {
          deque.addLast(deque.size());
        }
      }
    }).limit(100).collect(Collectors.toList());
    List<Callable<Object>> callableList = runnableLis.stream().map(Executors::callable)
        .collect(Collectors.toList());
    ExecutorService executorService = Executors.newFixedThreadPool(20);
    executorService.invokeAll(callableList);
    executorService.shutdown();
    boolean shouldBeEven = false;
    for (Integer integer : deque) {
      if (integer == PIVOT)
        shouldBeEven = true;
      printNumber(shouldBeEven,integer);
    }
  }

  static void printNumber(boolean shouldBeEven, int number){
    int numberToPrint = number;
    if(shouldBeEven){
         number = number +1;
    }if (numberToPrint == PIVOT){
      System.out.print(ANSI_YELLOW+numberToPrint+ANSI_RESET+",");
    }
    else if(number %2 == 0){
      System.out.print(ANSI_GREEN +numberToPrint+ANSI_RESET+",");
    }else {
      System.out.print(ANSI_RED +numberToPrint+ANSI_RESET+",");
    }

  }


}


