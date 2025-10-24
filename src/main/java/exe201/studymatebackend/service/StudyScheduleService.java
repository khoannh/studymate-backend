package exe201.studymatebackend.service;

import exe201.studymatebackend.dto.request.studyschedule.CreateStudyScheduleRequest;
import exe201.studymatebackend.dto.response.studyschedule.CreateStudyScheduleResponse;
import exe201.studymatebackend.dto.response.studyschedule.GetAllStudyScheduleOfRoomResponse;

public interface StudyScheduleService {
    CreateStudyScheduleResponse createStudySchedule(Integer roomID, CreateStudyScheduleRequest request);

    GetAllStudyScheduleOfRoomResponse getAllStudyScheduleOfRoom(Integer roomID, int page, int size);
}
