package Zad_3;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Przeczytaj i zastanów się co robi ten program i dlaczego nie działa poprawnie.
 * Spraw żeby zaczął :)
 * @author Jakub Czajka
 */
class Zad_3 {

  public static void main(String[] args) throws InterruptedException {
    ThreeDimetionPoint start = new ThreeDimetionPoint(0, 0, 0);
    List<Rocket> collect = Stream.generate(() -> new Rocket(start)).limit(100)
        .collect(Collectors.toList());
    ExecutorService executorService = Executors.newFixedThreadPool(10);
    executorService.invokeAll(collect);
    for (Rocket rocket:collect){
      System.out.println("I moved :" + rocket.getMoves() + "so now im in space " + rocket.whereAmI() );
    }
  }
}

class Rocket implements Callable<ThreeDimetionPoint> {

  ThreeDimetionPoint pointInSpace;
  int[] moves;


  Rocket(ThreeDimetionPoint pointInSpace) {
    moves = new int[4];
    this.pointInSpace = pointInSpace;
  }

  @Override
  public ThreeDimetionPoint call() {
     randomMove();
     return pointInSpace;
  }

  void randomMove() {
    do {
      moves[0]++;
      int distance =(ThreadLocalRandom.current().nextInt(1, 4));
      int direction =(ThreadLocalRandom.current().nextInt(1, 4));
      moves[direction] = moves[direction] + distance;
      pointInSpace.move(distance,direction);
    } while (!(ThreadLocalRandom.current().nextInt(1, 50) % 5 == 0));
  }

  public String getMoves() {
    return "["+moves[1]+","+moves[2]+","+moves[3]+"]";
  }

  public String whereAmI() {
    return pointInSpace.toString();
  }

}

class ThreeDimetionPoint {

  int x;
  int y;
  int z;

  public ThreeDimetionPoint(int x, int y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public ThreeDimetionPoint move(int distance, int direction) {
    switch (direction) {
      case 1:
        return moveX(distance);
      case 2:
        return moveY(distance);
      case 3:
        return moveZ(distance);
      default:
        return this;
    }
  }

  public ThreeDimetionPoint moveY(int move) {
    this.y = y + move;
    return this;
  }

  public ThreeDimetionPoint moveZ(int move) {
    this.z = z + move;
    return this;
  }

  public ThreeDimetionPoint moveX(int move) {
    this.x = x + move;
    return this;
  }

  @Override
  public String toString() {
    return "[" + x + "," + y + "," + z + ']';
  }
}
