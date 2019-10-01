package nl.snowmanxl.clickbattle.activities;


import nl.snowmanxl.clickbattle.game.ActivityType;

public interface ActivityFactory {

   Activity createNewActivity(ActivityType type);

}
