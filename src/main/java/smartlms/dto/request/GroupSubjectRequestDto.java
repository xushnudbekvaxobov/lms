package smartlms.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupSubjectRequestDto {
    @NotNull(message = "Group id is required")
    private UUID groupId;
    @NotNull(message = "Subject id is required")
    private UUID subjectId;
    @NotNull(message = "Teacher id is required")
    private UUID teacherId;
    @NotNull(message = "Semester is required")
    @Min(value = 1, message = "Semester must be 1 or 2")
    @Max(value = 2, message = "Semester must be 1 or 2")
    private Integer semester;
    @NotBlank(message = "Academic year is required")
    private String academicYear;
    private Double finalScore;
}
