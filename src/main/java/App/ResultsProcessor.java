package App;

import enums.Distance;
import enums.Gender;
import org.springframework.stereotype.Component;

import java.io.*;
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

    public void downloadResults(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            sportsmanList = bufferedReader.lines().map(line -> line.split(",")).map(this::buildSportsman).filter(Objects::nonNull)
                    .sorted().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sportsman[] getTopSportsmen(Distance distance, Gender gender, int limit) {

        int i = 0;
        if (sportsmanList == null) {
            System.out.println("Список спортсменов не загружен");
            return null;
        }
        Sportsman[] topSportsmen =  sportsmanList.stream().filter(Objects::nonNull).filter(sportsman -> sportsman.getDistance() == distance && sportsman.getGender() == gender)
                .limit(limit).toArray(Sportsman[]::new);
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
