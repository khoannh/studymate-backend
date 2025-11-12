package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.room.CreateRoomRequest;
import exe201.studymatebackend.dto.request.room.JoinRoomRequest;
import exe201.studymatebackend.dto.request.room.KickMemberRequest;
import exe201.studymatebackend.dto.response.room.*;

import java.util.List;


public interface RoomService {
    CreateRoomResponse createRoom(CreateRoomRequest request);

    void joinRoom(Integer roomID, JoinRoomRequest request);

    GetRoomPageResponse getAllRoom(int page, int size);

    GetRoomInfoResponse getRoomInfo(int roomID);

    GetMyRoomResponse getMyRoom(int page, int size);

    void leaveRoom(Integer roomID);

    List<GetAllMembersResponse> getMembers(Integer roomID);

    void kickMember(Integer roomID, KickMemberRequest request);
}
