package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.studyschedule.CreateStudyScheduleRequest;
import exe201.studymatebackend.dto.response.studyschedule.CreateStudyScheduleResponse;
import exe201.studymatebackend.dto.response.studyschedule.GetAllStudyScheduleOfRoomResponse;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.AccountRoom;
import exe201.studymatebackend.pojo.Room;
import exe201.studymatebackend.pojo.StudySchedule;
import exe201.studymatebackend.repository.AccountRoomRepository;
import exe201.studymatebackend.repository.RoomRepository;
import exe201.studymatebackend.repository.StudyScheduleRepository;
import exe201.studymatebackend.service.EmailService;
import exe201.studymatebackend.service.StudyScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StudyScheduleServiceImpl implements StudyScheduleService {

    @Autowired
    private StudyScheduleRepository studyScheduleRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AccountRoomRepository accountRoomRepository;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public CreateStudyScheduleResponse createStudySchedule(Integer roomID, CreateStudyScheduleRequest request) {
        Room room = roomRepository.findByRoomID(roomID);
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }
        StudySchedule newStudySchedule = new StudySchedule();
        newStudySchedule.setTitle(request.getTitle());
        newStudySchedule.setDescription(request.getDescription());
        newStudySchedule.setRoom(room);
        newStudySchedule.setMeetingLink(request.getMeetingLink());
        newStudySchedule.setStartTime(request.getStartTime());
        newStudySchedule.setEndTime(request.getEndTime());
        newStudySchedule.setNotified(false);
        newStudySchedule.setCreatedAt(LocalDateTime.now());
        studyScheduleRepository.save(newStudySchedule);

        List<AccountRoom> members = accountRoomRepository.findAllByRoomAndLeftAtIsNull(room);

        for (AccountRoom member : members) {
            String email = member.getAccount().getEmail();
            emailService.sendEmail(
                    email,
                    "📅 Lịch học mới được tạo!",
                    "Buổi học '" + newStudySchedule.getTitle() +
                            "' sẽ bắt đầu lúc " + newStudySchedule.getStartTime() +
                            ".\nLink tham gia: " + newStudySchedule.getMeetingLink()
            );
        }


        return CreateStudyScheduleResponse.builder()
                .scheduleID(newStudySchedule.getScheduleID())
                .title(newStudySchedule.getTitle())
                .description(newStudySchedule.getDescription())
                .meetingLink(newStudySchedule.getMeetingLink())
                .startTime(newStudySchedule.getStartTime())
                .endTime(newStudySchedule.getEndTime())
                .createdAt(LocalDateTime.now())
                .notified(false)
                .roomID(roomID)
                .status(newStudySchedule.getStatus())
                .build();
    }

    @Override
    public GetAllStudyScheduleOfRoomResponse getAllStudyScheduleOfRoom(Integer roomID, int page, int size) {
        Room room = roomRepository.findByRoomID(roomID);
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<StudySchedule> schedulePage = studyScheduleRepository.findByRoom(room, pageable);
        if (schedulePage.isEmpty()) {
            throw new AppException(ErrorCode.SCHEDULE_NOT_FOUND);
        }
        List<GetAllStudyScheduleOfRoomResponse.ScheduleResponse> scheduleResponses = schedulePage.getContent().stream().map(studySchedule -> GetAllStudyScheduleOfRoomResponse.ScheduleResponse.builder()
                .scheduleID(studySchedule.getScheduleID())
                .title(studySchedule.getTitle())
                .description(studySchedule.getDescription())
                .meetingLink(studySchedule.getMeetingLink())
                .startTime(studySchedule.getStartTime())
                .endTime(studySchedule.getEndTime())
                .createdAt(studySchedule.getCreatedAt())
                .notified(studySchedule.getNotified())
                .status(studySchedule.getStatus())
                .build()).toList();
        return GetAllStudyScheduleOfRoomResponse.builder()
                .roomID(roomID)
                .pageNumber(schedulePage.getNumber())
                .pageSize(schedulePage.getSize())
                .totalElements(schedulePage.getTotalElements())
                .totalPages(schedulePage.getTotalPages())
                .isLastPage(schedulePage.isLast())
                .schedules(scheduleResponses)
                .build();
    }


    @Scheduled(fixedRate = 60000)
    @Transactional
    public void sendStudyReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fifteenMinutesLater = now.plusMinutes(15);

        // Tìm tất cả lịch học chưa được nhắc và sắp diễn ra trong 15 phút tới
        List<StudySchedule> upcomingSchedules = studyScheduleRepository
                .findByNotifiedFalseAndStartTimeBetween(now, fifteenMinutesLater);

        for (StudySchedule schedule : upcomingSchedules) {
            Room room = schedule.getRoom();
            List<AccountRoom> members = accountRoomRepository.findAllByRoomAndLeftAtIsNull(room);

            for (AccountRoom member : members) {
                String email = member.getAccount().getEmail();
                emailService.sendEmail(
                        email,
                        "⏰ Nhắc nhở: Buổi học sắp bắt đầu!",
                        "Buổi học '" + schedule.getTitle() + "' sẽ bắt đầu lúc " +
                                schedule.getStartTime() +
                                ".\nLink tham gia: " + schedule.getMeetingLink()
                );
            }

            // Cập nhật lại để không gửi trùng lần nữa
            schedule.setNotified(true);
            studyScheduleRepository.save(schedule);
        }
    }
}
