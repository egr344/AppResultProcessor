import App.ProccesConfigurations;
import App.ResultsProcessor;
import App.Sportsman;
import enums.Distance;
import enums.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalTime;


public class AppMainTest {

    ApplicationContext appContext = new AnnotationConfigApplicationContext(ProccesConfigurations.class);
    private ResultsProcessor processor = appContext.getBean(ResultsProcessor.class);

    @Test
    public void testResults(){
        String path = "C:\\ex\\AppResultProcessir\\src\\main\\java\\App\\Table\\test.csv";
        Sportsman[] actual = processor.getTopSportsmen(path,Distance.TENKM,Gender.MALE,1);
        Sportsman[] expected = new Sportsman[] {new Sportsman("Иванов","Иван",Gender.MALE, Distance.TENKM, LocalTime.parse("00:55:20"))};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void testUpperLimitResults(){
        String path = "C:\\ex\\AppResultProcessir\\src\\main\\java\\App\\Table\\test.csv";
        Sportsman[] actual = processor.getTopSportsmen(path,Distance.TENKM,Gender.MALE,3);
        Sportsman[] expected = new Sportsman[] {new Sportsman("Иванов","Иван",Gender.MALE, Distance.TENKM, LocalTime.parse("00:55:20")),null,null};
        Assertions.assertArrayEquals(expected,actual);
    }

    @Test
    public void testNullName(){
        String path = "C:\\ex\\AppResultProcessir\\src\\main\\java\\App\\Table\\testNullName.csv";
        Sportsman[] actual = processor.getTopSportsmen(path,Distance.TENKM,Gender.MALE,3);
        Sportsman[] expected = new Sportsman[] {new Sportsman(null,null,Gender.MALE, Distance.TENKM, LocalTime.parse("00:55:20")),null,null};
        Assertions.assertArrayEquals(expected,actual);
    }
}
