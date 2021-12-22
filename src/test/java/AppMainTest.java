import App.ProccesConfigurations;
import App.ResultsProcessor;
import App.Sportsman;
import enums.Distance;
import enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;


public class AppMainTest {

    ApplicationContext appContext = new AnnotationConfigApplicationContext(ProccesConfigurations.class);
    private ResultsProcessor processor = appContext.getBean(ResultsProcessor.class);

    @Test
    public void testDownloadFile() throws NoSuchFieldException, IllegalAccessException {
        String path = "src/main/java/App/Table/test.csv";
        File file = new File(path);
        processor.downloadResults(file);
        Field sportsmanList = processor.getClass().getDeclaredField("sportsmanList");
        sportsmanList.setAccessible(true);
        List<Sportsman> actual =(List<Sportsman>) sportsmanList.get(processor);
        List<Sportsman> expected = Arrays.asList(new Sportsman("Иванов", "Иван", Gender.MALE, Distance.TENKM, LocalTime.parse("00:55:20")));
        Assertions.assertEquals(expected,actual);

    }

    @Test
    public void testResults() {
        String path = "src/main/java/App/Table/test.csv";
        File file = new File(path);
        processor.downloadResults(file);
        Sportsman[] actual = processor.getTopSportsmen(Distance.TENKM, Gender.MALE, 1);
        Sportsman[] expected = new Sportsman[]{new Sportsman("Иванов", "Иван", Gender.MALE, Distance.TENKM, LocalTime.parse("00:55:20"))};
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testUpperLimitResults() {
        String path = "src/main/java/App/Table/test.csv";
        File file = new File(path);
        processor.downloadResults(file);
        Sportsman[] actual = processor.getTopSportsmen(Distance.TENKM, Gender.MALE, 3);
        Sportsman[] expected = new Sportsman[]{new Sportsman("Иванов", "Иван", Gender.MALE, Distance.TENKM, LocalTime.parse("00:55:20"))};
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testNullName() {
        String path = "src/main/java/App/Table/testNullName.csv";
        File file = new File(path);
        processor.downloadResults(file);
        Sportsman[] actual = processor.getTopSportsmen(Distance.TENKM, Gender.MALE, 1);
        Sportsman[] expected = new Sportsman[]{new Sportsman(null, null, Gender.MALE, Distance.TENKM, LocalTime.parse("00:55:20"))};
        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetTopSportsmenWithoutDownloadData(){
        Sportsman[] actual = processor.getTopSportsmen(Distance.TENKM, Gender.MALE, 3);
        Sportsman[] expected = null;
        Assertions.assertArrayEquals(expected, actual);
    }
}
