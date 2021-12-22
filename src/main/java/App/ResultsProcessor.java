package App;

import enums.Distance;
import enums.Gender;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ResultsProcessor {

    private List<Sportsman> sportsmanList;

    private void downloadResults(String path) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            //String[] sportsmenString =
            sportsmanList = bufferedReader.lines().map(line -> line.split(",")).map(this::buildSportsman).filter(Objects::nonNull).sorted().collect(Collectors.toList());

        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sportsman[] getTopSportsmen(String path,Distance distance, Gender gender, int limit) {
        downloadResults(path);
        Sportsman[] topSportsmen = new Sportsman[limit];
        int i = 0;
        if (sportsmanList == null) {
            System.out.println("Список спортсменов не загружен");
            return topSportsmen;
        }
        for (Sportsman sportsman : sportsmanList) {
            if (sportsman.getDistance() == distance && sportsman.getGender() == gender) {
                topSportsmen[i] = sportsman;
                i += 1;
            }
            if (i == limit)
                break;
        }
        return topSportsmen;
    }

    private Sportsman buildSportsman(String[] paramSportsmen) {
        paramSportsmen = Arrays.stream(paramSportsmen).map(String::trim).toArray(String[]::new);
        if (paramSportsmen.length != 4)
            return null;

        Gender gender;
        if (paramSportsmen[1].equals(Gender.MALE.getValue())) {
            gender = Gender.MALE;
        } else if (paramSportsmen[1].equals(Gender.FEMALE.getValue())) {
            gender = Gender.FEMALE;
        } else {
            System.out.println("пол спорстмена неккоректен неккоректны");
            return null;
        }

        Distance distance;
        if (paramSportsmen[2].equals(Distance.FIVEKM.getDistanceInKm()))
            distance = Distance.FIVEKM;
        else if (paramSportsmen[2].equals(Distance.TENKM.getDistanceInKm()))
            distance = Distance.TENKM;
        else
            return null;

        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().appendPattern("mm:ss")
                .parseDefaulting(ChronoField.CLOCK_HOUR_OF_DAY, 0).toFormatter();
        LocalTime time;
        try {
             time = LocalTime.parse(paramSportsmen[3], timeFormatter);
        } catch (DateTimeParseException e) {
            e.getMessage();
            return null;
        }

        String[] surnameAndName = paramSportsmen[0].trim().split(" ");
        if (surnameAndName.length != 2) {
            System.out.println("Неккоректно задананно имя и фамилия");
            surnameAndName = new String[2];
        }

        return new Sportsman(surnameAndName[0], surnameAndName[1], gender, distance, time);

    }
}
