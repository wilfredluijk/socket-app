package nl.snowmanxl.clickbattle.activities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.snowmanxl.clickbattle.model.SimpleParticipant;

@JsonDeserialize(as = ClickRaceActivityData.class)
public interface ActivityData {
}
