package smartlms.dto.request;

import jakarta.validation.constraints.*;
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
    @Pattern(regexp = "^\\d{4}-\\d{4}$", message = "Academic year must follow the format 'YYYY-YYYY' (e.g., 2025-2026)")
    private String academicYear;
    @Positive(message = "FinalScore must be positive")
    private Double finalScore;
}
