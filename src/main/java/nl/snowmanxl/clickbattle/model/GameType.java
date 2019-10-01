package nl.snowmanxl.clickbattle.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.snowmanxl.clickbattle.activities.Activity;
import nl.snowmanxl.clickbattle.game.ClickRace;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface GameType extends Activity {

}
