package smartlms.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupResponseDto {
    private UUID id;
    private String name;
    private Integer course;
    private String faculty;
}
