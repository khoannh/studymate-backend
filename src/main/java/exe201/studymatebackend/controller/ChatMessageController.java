package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.message.GetAllMessageOfRoomResponse;
import exe201.studymatebackend.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message-management")
public class ChatMessageController {

    @Autowired
    private ChatMessageService chatMessageService;

//    @PostMapping("/send")
//    public ApiResponse<SendMessageResponse> sendMessage(@Valid @RequestBody SendMessageRequest sendMessageRequest) {
//        SendMessageResponse result = chatMessageService.sendMessage(sendMessageRequest);
//        return ApiResponse.<SendMessageResponse>builder()
//                .code(200)
//                .message("Message sent successfully")
//                .result(result)
//                .build();
//    }

    @GetMapping("/messages/{roomID}")
    public ApiResponse<GetAllMessageOfRoomResponse> getMessages(
            @PathVariable Integer roomID,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        GetAllMessageOfRoomResponse result = chatMessageService.getAllMessageOfRoom(roomID, page, size);
        return ApiResponse.<GetAllMessageOfRoomResponse>builder()
                .code(200)
                .message("Lấy danh sách tin nhắn thành công")
                .result(result)
                .build();
    }


}
