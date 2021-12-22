package App;

import enums.Distance;
import enums.Gender;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Objects;

public class AppMain {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(ProccesConfigurations.class);
        ResultsProcessor processor = appContext.getBean(ResultsProcessor.class);
        String path = "C:\\ex\\AppResultProcessir\\src\\main\\java\\App\\Table\\test.csv";
        processor.getTopSportsmen(path,Distance.TENKM, Gender.MALE,4);
    }
}
