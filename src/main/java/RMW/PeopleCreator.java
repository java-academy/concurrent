package RMW;

import io.codearte.jfairy.producer.person.Person;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Jakub Czajka
 */
class PeopleCreator implements Runnable {

  People people;
  final int numberOfMenToCreate;
  final int numberOfWomenToCreate;

  public PeopleCreator(int numberOfMenToCreate, int numberOfWomenToCreate, People outcome) {
    this.numberOfMenToCreate = numberOfMenToCreate;
    this.numberOfWomenToCreate = numberOfWomenToCreate;
    this.people = outcome;
  }

  @Override
  public void run() {
    ExecutorService executorService = Executors.newFixedThreadPool(50);
    List<PersonCreationTask> peopleCreators = Stream.generate(PersonCreationTask::generateMan).limit(numberOfMenToCreate).collect(Collectors.toList());
    Stream.generate(PersonCreationTask::generateWoman).limit(numberOfWomenToCreate).forEach(peopleCreators::add);
    peopleCreators.forEach(personCreationTask -> personCreationTask.setOutcome(people));

      try {
        executorService.invokeAll(peopleCreators);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

    executorService.shutdown();
  }


}
