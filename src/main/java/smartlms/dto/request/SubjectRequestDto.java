package smartlms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectRequestDto {
    @NotBlank(message = "Subject name can not be null")
    private String name;
    @NotBlank(message = "Subject code can not be null")
    private String code;
}