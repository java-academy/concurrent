package RMW;

import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.person.Person.Sex;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jakub Czajka
 */
class People {

  List<Person> menList;
  List<Person> womenList;
  int men;
  int women;

  People() {
    this.men = 0;
    this.women = 0;
    menList = new LinkedList<>();
    womenList = new LinkedList<>();
  }

  private void addMan(Person person) {
    menList.add(person);
  }

  void addPerson(Person person) {
    if (person.sex().equals(Sex.MALE)) {
      addMan(person);
      men++;
    } else {
      addWoman(person);
      women++;
    }
  }

  private void addWoman(Person person) {
    womenList.add(person);
  }

  int getNumberOfPeople() {
    return men + women;
  }

   List<Person> getPeopleList() {
    List<Person> people = new ArrayList<>(menList);
    people.addAll(womenList);
    return people;
  }


}




