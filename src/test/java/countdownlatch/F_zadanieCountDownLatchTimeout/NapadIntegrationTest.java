package countdownlatch.F_zadanieCountDownLatchTimeout;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import countdownlatch.F_zadanieCountDownLatchTimeout.Napad.MetadaneNapadu;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Kacper Staszek
 * @author Marcin Ogorzałek
 */
@Test
public class NapadIntegrationTest {

  @Test(invocationCount = 100, threadPoolSize = 100,
      dataProvider = "failedRobberyConditions", successPercentage = 90)
  public void whenThievesAreCatchThenGoToJail(int teamSize,
      long timeToPoliceArrive, int robberyTime) {
    // Given
    Napad napad = new Napad(teamSize, timeToPoliceArrive, robberyTime);
    MetadaneNapadu given = napad.tworzeniePotrzebnychObiektów();
    // When
    napad.uruchamianieWątków(given);
    try {
      MILLISECONDS.sleep(robberyTime);
    } catch (InterruptedException ignored) {
      System.err.println(ignored.getMessage());
    }
    // Then
    assertEquals(given.więzienie.getWięźniowie().size(), given.zatrzaskKierowcy.getCount());
  }

  @Test(invocationCount = 100, threadPoolSize = 100,
      dataProvider = "succeedRobberyConditions", successPercentage = 90)
  public void whenPoliceIsLateToRobberyJailIsEmpty(int teamSize,
      long timeToPoliceArrive, int robberyTime) {
    // Given
    Napad napad = new Napad(teamSize, timeToPoliceArrive, robberyTime);
    MetadaneNapadu given = napad.tworzeniePotrzebnychObiektów();
    // When
    napad.uruchamianieWątków(given);
    try {
      MILLISECONDS.sleep(robberyTime);
    } catch (InterruptedException ignored) {
      System.err.println(ignored.getMessage());
    }
    // Then
    assertEquals(given.więzienie.getWięźniowie().size(), 0);
  }

  @Test(invocationCount = 100, threadPoolSize = 100,
      dataProvider = "illegalTeamSize", expectedExceptions = IllegalArgumentException.class,
      successPercentage = 90)
  public void whenTeamSizeZeroOrLessThenThrowsIllegalArgumentException(int teamSize,
      long timeToPoliceArrive, int robberyTime) {
    // Given
    Napad napad = new Napad(teamSize, timeToPoliceArrive, robberyTime);
    MetadaneNapadu given = napad.tworzeniePotrzebnychObiektów();
    // When
    napad.uruchamianieWątków(given);
    try {
      MILLISECONDS.sleep(robberyTime);
    } catch (InterruptedException ignored) {
      System.err.println(ignored.getMessage());
    }
    // Then
    fail();
  }

  @DataProvider
  public static Object[][] failedRobberyConditions() {
    return new Object[][]{
        {4, 5000, 10_000},
        {5, 1000, 1500},
        {6, 110, 150},
    };
  }

  @DataProvider
  public static Object[][] succeedRobberyConditions() {
    return new Object[][]{
        {4, 10_000, 5000},
        {5, 1500, 1000},
        {6, 150, 110},
        {6, 1000, 500},
    };
  }

  @DataProvider
  public static Object[][] illegalTeamSize() {
    return new Object[][]{
        {0, 0, 0},
        {-5, 1500, 1000},
        {0, 1500, 1000},
        {-6, 150, 110},
        {-1, 1000, 500},
    };
  }
}