package nl.snowmanxl.clickbattle.messages.rest;

public class RestResponse {
    String message;
    MessageType type;

    public RestResponse(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public RestResponse(String message, MessageType type) {
        this.message = message;
        this.type = type;
    }
}

