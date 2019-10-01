package nl.snowmanxl.clickbattle.activities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS)
public interface ActivityData {
}
