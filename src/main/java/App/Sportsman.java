package App;

import enums.Distance;
import enums.Gender;

import java.time.LocalTime;
import java.util.Objects;

public class Sportsman implements Comparable<Sportsman> {

    private String name;
    private Distance distance;
    private Gender gender;

    public LocalTime getTime() {
        return time;
    }

    public String getSurname() {
        return surname;
    }

    private LocalTime time;
    private String surname;

    public Sportsman(String surname, String name, Gender gender, Distance distance, LocalTime time) {
        this.name = name;
        this.distance = distance;
        this.gender = gender;
        this.surname = surname;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }


    @Override
    public int compareTo(Sportsman o) {
        return this.getTime().compareTo(o.getTime());
    }

    @Override
    public String toString() {
        return surname+" "+name+", "+gender+", "+distance.getDistanceInKm()+", "+time.getMinute()+":"+time.getSecond();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sportsman sportsman = (Sportsman) o;
        return Objects.equals(name, sportsman.name) && distance == sportsman.distance && gender == sportsman.gender && time.equals(sportsman.time) && Objects.equals(surname, sportsman.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, distance, gender, time, surname);
    }
}
