package Zad_5;

/**
 * Przeczytaj i zastanów się co robi ten program i dlaczego nie działa poprawnie.
 * Uruchom i daj mu chwilę, Dokonaj zmian w klasach tak aby zaczął działać poprwanie;
 * @author Jakub Czajka
 */
class Main {
  final static int NUMBER_OF_MEN_TO_CREATE = 1000;
  final static int NUMBER_OF_WOMEN_TO_CREATE = 1000;
  public static void main(String[] args) throws InterruptedException {
     People people = new People();
    PeopleCreator peopleCreator = new PeopleCreator(NUMBER_OF_MEN_TO_CREATE,NUMBER_OF_WOMEN_TO_CREATE, people);
    Thread thread = new Thread(peopleCreator,"People_Creation");
    thread.start();
    thread.join();
    System.out.println("People created " + people.getNumberOfPeople());
    System.out.println("List<People> size " + people.getPeopleList().size());
  }

}
