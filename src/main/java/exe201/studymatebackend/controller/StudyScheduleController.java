package exe201.studymatebackend.controller;

import exe201.studymatebackend.dto.request.studyschedule.CreateStudyScheduleRequest;
import exe201.studymatebackend.dto.response.ApiResponse;
import exe201.studymatebackend.dto.response.studyschedule.CreateStudyScheduleResponse;
import exe201.studymatebackend.dto.response.studyschedule.GetAllStudyScheduleOfRoomResponse;
import exe201.studymatebackend.service.StudyScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schedule-management")
public class StudyScheduleController {

    @Autowired
    private StudyScheduleService studyScheduleService;

    @PostMapping("/schedule/{roomID}")
    public ApiResponse<CreateStudyScheduleResponse> createStudySchedule(@Valid @RequestBody CreateStudyScheduleRequest request, @PathVariable Integer roomID) {
        CreateStudyScheduleResponse result = studyScheduleService.createStudySchedule(roomID, request);
        return ApiResponse.<CreateStudyScheduleResponse>builder()
                .code(HttpStatus.CREATED.value())
                .result(result)
                .message("Tạo lịch học thành công")
                .build();
    }

    @GetMapping("/schedule/{roomID}")
    public ApiResponse<GetAllStudyScheduleOfRoomResponse> getAllStudyScheduleOfRoom(@PathVariable Integer roomID,
                                                                                    @RequestParam(defaultValue = "0") int pageNumber,
                                                                                    @RequestParam(defaultValue = "10") int pageSize
    ) {
        GetAllStudyScheduleOfRoomResponse result = studyScheduleService.getAllStudyScheduleOfRoom(roomID, pageNumber, pageSize);
        return ApiResponse.<GetAllStudyScheduleOfRoomResponse>builder()
                .code(HttpStatus.OK.value())
                .result(result)
                .message("Lấy danh sách lịch học thành công")
                .build();
    }


}
