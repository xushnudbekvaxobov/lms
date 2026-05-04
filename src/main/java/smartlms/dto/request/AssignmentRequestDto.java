package smartlms.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentRequestDto {
    @NotBlank(message = "Title can not be null")
    private String title;
    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be a future date")
    private LocalDateTime dueDate;
    @NotNull(message = "MaxScore can not be 0")
    @Positive(message = "MaxScore must be a positive value greater than zero")
    private Double maxScore;
}
