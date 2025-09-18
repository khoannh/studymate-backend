package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.room.CreateRoomRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.room.CreateRoomResponse;
import exe201.studymatebackend.dto.response.room.GetRoomPageResponse;
import exe201.studymatebackend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
                .message("Room created successfully")
                .resutl(result)
                .build();
    }

    @PostMapping("/room/{roomID}/join")
    public ApiResponse<Void> joinRoom(@PathVariable Integer roomID) {
        roomService.joinRoom(roomID);
        return ApiResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Joined room successfully")
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
                .message("Retrieved all rooms successfully")
                .resutl(result)
                .build();
    }

}
