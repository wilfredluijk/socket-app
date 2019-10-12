package nl.snowmanxl.socketapp.messages.socket;


import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface SocketMessage {
}
