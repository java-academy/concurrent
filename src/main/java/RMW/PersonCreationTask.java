package RMW;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.person.Person.Sex;
import java.util.concurrent.Callable;

/**
 * @author Jakub Czajka
 */
class PersonCreationTask implements Callable<Person> {

Sex sex;
People people;

  private PersonCreationTask(Sex sex) {
    this.sex = sex;
  }

  static PersonCreationTask generateWoman(){
    return new PersonCreationTask(Sex.FEMALE);
  }

  static PersonCreationTask generateMan(){
    return new PersonCreationTask(Sex.MALE);
  }


  public void setOutcome(People people) {
    this.people = people;
  }

  @Override
  public Person call() throws Exception {
    Person person;
    Fairy fairy = Fairy.create();
    do {
      person = fairy.person();
    }while (person.sex().equals(sex));
    people.addPerson(person);
    return null;
  }
}
