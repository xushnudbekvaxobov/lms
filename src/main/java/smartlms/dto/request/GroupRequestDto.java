package smartlms.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupRequestDto {
    @NotBlank(message = "Group name can not be bull")
    private String name;
    @NotNull(message = "Course can not be 0. It is between 1 and 4")
    @Min(value = 1, message = "Course must be at least 1")
    @Max(value = 4, message = "Course can not exceed 4")
    private Integer course;
    @NotBlank(message = "Faculty can not be null")
    private String faculty;
}
