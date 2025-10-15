package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.room.CreateRoomRequest;
import exe201.studymatebackend.dto.response.room.CreateRoomResponse;
import exe201.studymatebackend.dto.response.room.GetMyRoomResponse;
import exe201.studymatebackend.dto.response.room.GetRoomInfoResponse;
import exe201.studymatebackend.dto.response.room.GetRoomPageResponse;


public interface RoomService {
    CreateRoomResponse createRoom(CreateRoomRequest request);

    void joinRoom(Integer roomID);

    GetRoomPageResponse getAllRoom(int page, int size);

    GetRoomInfoResponse getRoomInfo(int roomID);

    GetMyRoomResponse getMyRoom(int page, int size);

    void leaveRoom(Integer roomID);
}
