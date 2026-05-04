package smartlms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupSubjectResponseDtoForAdmin {
    private Integer semester;
    private String academicYear;
    private TeacherResponseDtoForStudents teacher;
    private SubjectResponseDto subject;
}
