package smartlms.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectResponseDto {
    private UUID id;
    private String name;
    private String code;
}
