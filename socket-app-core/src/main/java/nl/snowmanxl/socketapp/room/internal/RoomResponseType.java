package nl.snowmanxl.socketapp.room.internal;

import nl.snowmanxl.socketapp.messages.rest.MessageType;

public enum RoomResponseType implements MessageType {
    CONFIRMATION, GET_PLAYER_ID, GET_ROOM_ID, GET_GAME_TYPE
}
