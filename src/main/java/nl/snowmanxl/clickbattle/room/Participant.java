package nl.snowmanxl.clickbattle.room;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import nl.snowmanxl.clickbattle.model.SimpleParticipant;

@JsonDeserialize(as = SimpleParticipant.class)
public interface Participant {

    String getName();

    String getId();

    void setId(String id);

}
