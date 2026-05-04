package smartlms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import smartlms.entity.enums.WeekDay;

import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequestDto {
    @NotNull(message = "Group subject cannot be null")
    private UUID groupSubjectId;
    @NotNull(message = "Week day must be specified")
    private WeekDay weekDay;
    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;
    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;
    @NotBlank(message = "Room cannot be empty")
    private String room;
    @NotBlank(message = "Lesson type cannot be empty")
    private String lessonType;
}
