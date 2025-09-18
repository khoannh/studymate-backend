package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.room.CreateRoomRequest;
import exe201.studymatebackend.dto.response.room.CreateRoomResponse;
import exe201.studymatebackend.dto.response.room.GetRoomPageResponse;


public interface RoomService {
    CreateRoomResponse createRoom(CreateRoomRequest request);

    void joinRoom(Integer roomID);

    GetRoomPageResponse getAllRoom(int page, int size);
}
