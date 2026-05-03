package smartlms.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentResponseDto {
    private UUID id;
    private String title;
    private LocalDateTime dueDate;
    private Double maxScore;
}
