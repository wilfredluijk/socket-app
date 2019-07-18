package nl.snowmanxl.clickbattle.activities;

public class ClickRaceProvider implements ActivityProvider<ClickRace> {

    @Override
    public Class<ClickRace> getTypeOfProvider() {
        return ClickRace.class;
    }

    @Override
    public ClickRace getNewActivity() {
        return new ClickRace();
    }
}
