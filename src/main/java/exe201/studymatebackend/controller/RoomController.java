package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.room.CreateRoomRequest;
import exe201.studymatebackend.dto.request.room.JoinRoomRequest;
import exe201.studymatebackend.dto.request.room.KickMemberRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.room.*;
import exe201.studymatebackend.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room-management")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/room")
    public ApiResponse<CreateRoomResponse> createRoom(@RequestBody CreateRoomRequest createRoomRequest) {
        CreateRoomResponse result = roomService.createRoom(createRoomRequest);
        return ApiResponse.<CreateRoomResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Tạo phòng thành công")
                .result(result)
                .build();
    }

    @PostMapping("/room/{roomID}/join")
    public ApiResponse<Void> joinRoom(@PathVariable Integer roomID, @RequestBody(required = false) JoinRoomRequest request) {
        roomService.joinRoom(roomID, request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Tham gia phòng thành công")
                .build();
    }

    @GetMapping("/rooms")
    public ApiResponse<GetRoomPageResponse> getRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        GetRoomPageResponse result = roomService.getAllRoom(page, size);
        return ApiResponse.<GetRoomPageResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách phòng thành công")
                .result(result)
                .build();
    }

    @GetMapping("rooms/{roomID}")
    public ApiResponse<GetRoomInfoResponse> getRoomInfo(@PathVariable Integer roomID) {
        GetRoomInfoResponse result = roomService.getRoomInfo(roomID);
        return ApiResponse.<GetRoomInfoResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy thông tin phòng thành công")
                .result(result)
                .build();
    }

    @GetMapping("/my-rooms")
    public ApiResponse<GetMyRoomResponse> getMyRooms(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        GetMyRoomResponse result = roomService.getMyRoom(page, size);
        return ApiResponse.<GetMyRoomResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách phòng của tôi thành công")
                .result(result)
                .build();
    }

    @PostMapping("/room/{roomID}/leave")
    public ApiResponse<Void> leaveRoom(@PathVariable Integer roomID) {
        roomService.leaveRoom(roomID);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Rời phòng thành công")
                .build();
    }

    @GetMapping("/room/{roomID}/members")
    public ApiResponse<List<GetAllMembersResponse>> getMembers(@PathVariable Integer roomID) {
        List<GetAllMembersResponse> result = roomService.getMembers(roomID);
        return ApiResponse.<List<GetAllMembersResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Lấy danh sách thành viên thành công")
                .result(result)
                .build();
    }

    @PostMapping("/room/{roomID}/kick")
    public ApiResponse<Void> kickMember(@Valid @RequestBody KickMemberRequest request, @PathVariable Integer roomID) {
        roomService.kickMember(roomID, request);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Xóa thành viên thành công")
                .build();
    }

    @PutMapping("/room/{roomID}/status")
    public ApiResponse<Void> updateRoomStatus(
            @PathVariable Integer roomID,
            @RequestParam boolean isActive
    ) {
        roomService.updateRoomStatus(roomID, isActive);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message(isActive ? "Kích hoạt phòng thành công" : "Vô hiệu hóa phòng thành công")
                .build();
    }

}
