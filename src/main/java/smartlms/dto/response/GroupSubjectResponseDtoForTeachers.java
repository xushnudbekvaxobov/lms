package smartlms.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupSubjectResponseDtoForTeachers {
    private Long studentCount;
    private GroupResponseDto group;
    private SubjectResponseDto subject;
}
