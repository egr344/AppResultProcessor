package enums;

public enum Gender {
    FEMALE("Ж"),MALE("М");

    public String getValue() {
        return value;
    }

    private final String value;


    Gender(String value) {
        this.value = value;
    }
}
