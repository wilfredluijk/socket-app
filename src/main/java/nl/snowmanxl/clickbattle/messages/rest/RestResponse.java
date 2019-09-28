package nl.snowmanxl.clickbattle.messages.rest;

import nl.snowmanxl.clickbattle.room.internal.RoomResponseType;

public class RestResponse {
    String message;
    RoomResponseType type;

    public RestResponse(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public RoomResponseType getType() {
        return type;
    }

    public void setType(RoomResponseType type) {
        this.type = type;
    }

    public RestResponse(String message, RoomResponseType type) {
        this.message = message;
        this.type = type;
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}

