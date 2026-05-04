package smartlms.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smartlms.entity.enums.LessonType;
import smartlms.entity.enums.WeekDay;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ScheduleResponseDto {
    private UUID id;
    private UUID groupSubjectId;
    private WeekDay weekDay;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer durationMinutes;
    private String room;
    private LessonType lessonType;

    /**
     * Duration avtomatik hisob qilinadi
     */
    public Integer getDurationMinutes() {
        if (startTime != null && endTime != null) {
            return (int) java.time.temporal.ChronoUnit.MINUTES.between(startTime, endTime);
        }
        return durationMinutes;
    }
}

