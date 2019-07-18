package nl.snowmanxl.clickbattle.activities;

public interface ActivityProvider<T extends Activity> {

    Class<T> getTypeOfProvider();

    T getNewActivity();
}
