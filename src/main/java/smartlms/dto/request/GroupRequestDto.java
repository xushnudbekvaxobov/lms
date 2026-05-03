package smartlms.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRequestDto {
    private String name;
    private Integer course;
    private String faculty;
}
