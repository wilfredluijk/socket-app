package nl.snowmanxl.clickbattle.activities;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import nl.snowmanxl.clickbattle.model.SimpleParticipant;

import java.io.IOException;

public class ParticipantKeyDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) throws IOException {
        return new SimpleParticipant(s);
    }
}
