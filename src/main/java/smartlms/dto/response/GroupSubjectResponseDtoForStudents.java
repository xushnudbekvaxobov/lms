package smartlms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupSubjectResponseDtoForStudents {
        UUID id;
        Integer semester;
        Long nbCount;
        SubjectResponseDto subject;
        TeacherResponseDtoForStudents teacher;
}
