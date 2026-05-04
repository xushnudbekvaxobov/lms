package smartlms.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import smartlms.dto.request.ScheduleRequestDto;
import smartlms.entity.GroupSubjectEntity;
import smartlms.entity.ScheduleEntity;
import smartlms.entity.enums.LessonType;
import smartlms.exception.DataNotFoundException;
import smartlms.repository.GroupSubjectRepository;
import smartlms.repository.ScheduleRepository;
import smartlms.service.ScheduleService;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupSubjectRepository groupSubjectRepository;

    @Transactional
    @Override
    public void createSchedule(ScheduleRequestDto scheduleRequestDto) {
        log.info("Creating schedule for group subject: {} on {} from {} to {}",
                scheduleRequestDto.getGroupSubjectId(),
                scheduleRequestDto.getWeekDay(),
                scheduleRequestDto.getStartTime(),
                scheduleRequestDto.getEndTime());

        // Validate time logic
        if (scheduleRequestDto.getStartTime().isAfter(scheduleRequestDto.getEndTime())) {
            log.error("Start time cannot be after end time for schedule");
            throw new IllegalArgumentException("Start time must be before end time");
        }

        // Check room conflict
        boolean hasConflict = scheduleRepository.existsConflict(
                scheduleRequestDto.getRoom(),
                scheduleRequestDto.getWeekDay(),
                scheduleRequestDto.getStartTime(),
                scheduleRequestDto.getEndTime()
        );
        if (hasConflict) {
            log.error("Room {} is already booked on {} from {} to {}",
                    scheduleRequestDto.getRoom(),
                    scheduleRequestDto.getWeekDay(),
                    scheduleRequestDto.getStartTime(),
                    scheduleRequestDto.getEndTime());
            throw new IllegalArgumentException("Room is already booked at the same time on " + scheduleRequestDto.getWeekDay());
        }

        GroupSubjectEntity groupSubject = groupSubjectRepository.findById(scheduleRequestDto.getGroupSubjectId()).orElseThrow(() -> {
                    log.error("GroupSubject not found with id: {}", scheduleRequestDto.getGroupSubjectId());
                    return new DataNotFoundException("GroupSubject not found with id: " + scheduleRequestDto.getGroupSubjectId());
                });

        LessonType lessonType;
        try {
            lessonType = LessonType.valueOf(scheduleRequestDto.getLessonType().toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Invalid lesson type: {}. Allowed values: {}", scheduleRequestDto.getLessonType(), LessonType.values());
            throw new IllegalArgumentException("Invalid lesson type. Allowed values: LECTURE, PRACTICE, LAB, SEMINAR");
        }

        ScheduleEntity schedule = ScheduleEntity.builder()
                .groupSubject(groupSubject)
                .weekDay(scheduleRequestDto.getWeekDay())
                .startTime(scheduleRequestDto.getStartTime())
                .endTime(scheduleRequestDto.getEndTime())
                .room(scheduleRequestDto.getRoom())
                .lessonType(lessonType)
                .build();

        scheduleRepository.save(schedule);
        log.info("Schedule created successfully with id: {}", schedule.getId());
    }
}
