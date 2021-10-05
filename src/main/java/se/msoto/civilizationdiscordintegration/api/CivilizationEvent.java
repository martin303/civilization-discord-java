package se.msoto.civilizationdiscordintegration.api;

public class CivilizationEvent {

    // Name of the game
    private String value1;
    // User that has next turn
    private String value2;
    // Round
    private String value3;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getValue3() {
        return value3;
    }

    public void setValue3(String value3) {
        this.value3 = value3;
    }

    public String getGameName() {
        return value1;
    }

    public String getNextUser() {
        return value2;
    }

    public String getCurrentTurn() {
        return value3;
    }
}
