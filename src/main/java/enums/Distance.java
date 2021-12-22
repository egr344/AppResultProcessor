package enums;

public enum Distance {
    FIVEKM(5),TENKM(10);
    private final int value;
    Distance(int value){
        this.value = value;
    }
    public int getValue(){
        return value;
    }
    public String getDistanceInKm(){
        return value + " км";
    }
}
