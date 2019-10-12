package nl.snowmanxl.socketapp.activities;


import nl.snowmanxl.socketapp.game.ActivityType;

public interface ActivityFactory {

   Activity createNewActivity(ActivityType type);

}
