package nl.snowmanxl.clickbattle.activities;


import nl.snowmanxl.clickbattle.activities.Activity;

public interface ActivityFactory {

   Activity createNewActivity(Activity type);

}
