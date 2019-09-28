package nl.snowmanxl.clickbattle.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.activities.ClickRace;

@JsonDeserialize(as = ClickRace.class)
public interface GameType extends Activity {

}
