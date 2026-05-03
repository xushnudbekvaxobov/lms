package smartlms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentResponseDtoForStudents {
    private String title;
    private LocalDateTime dueDate;
    private Double maxScore;
    private TeacherResponseDtoForStudents teacher;
    private SubjectResponseDto subject;
    private GradeResponseDto grade;
}
