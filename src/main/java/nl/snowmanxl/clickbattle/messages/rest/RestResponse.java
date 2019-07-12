package nl.snowmanxl.clickbattle.messages.rest;

public class RestResponse {
    String message;
    String type;

    public RestResponse(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public RestResponse(String message, String type) {
        this.message = message;
        this.type = type;
    }
}

