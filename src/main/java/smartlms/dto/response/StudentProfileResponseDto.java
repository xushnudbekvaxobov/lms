package smartlms.dto.response;

import lombok.*;
import smartlms.entity.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentProfileResponseDto {
    private UUID Id;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private GroupResponseDto group;
}
