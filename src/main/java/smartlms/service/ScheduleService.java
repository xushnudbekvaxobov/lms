package smartlms.service;

import org.springframework.stereotype.Service;
import smartlms.dto.request.ScheduleRequestDto;

@Service
public interface ScheduleService {
    void createSchedule(ScheduleRequestDto scheduleRequestDto);
}
