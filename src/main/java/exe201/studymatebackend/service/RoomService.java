package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.room.CreateRoomRequest;
import exe201.studymatebackend.dto.response.room.*;

import java.util.List;


public interface RoomService {
    CreateRoomResponse createRoom(CreateRoomRequest request);

    void joinRoom(Integer roomID);

    GetRoomPageResponse getAllRoom(int page, int size);

    GetRoomInfoResponse getRoomInfo(int roomID);

    GetMyRoomResponse getMyRoom(int page, int size);

    void leaveRoom(Integer roomID);

    List<GetAllMembersResponse> getMembers(Integer roomID);
}
