package smartlms.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponseDtoForAdmin {
    private Long studentCount;
    private Long subjectCount;
    private GroupResponseDto group;
}
