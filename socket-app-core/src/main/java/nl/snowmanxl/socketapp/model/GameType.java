package nl.snowmanxl.socketapp.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import nl.snowmanxl.socketapp.activities.Activity;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface GameType extends Activity {

}
