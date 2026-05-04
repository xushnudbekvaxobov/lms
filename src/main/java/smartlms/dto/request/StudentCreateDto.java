package smartlms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import smartlms.entity.enums.Gender;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCreateDto{
    @NotBlank(message = "First name cannot be empty")
    private String firstName;
    @NotBlank(message = "Last name cannot be empty")
    private String lastName;
    @NotNull(message = "Birth date must be provided")
    @Past(message = "BirthDate must be in the past")
    private LocalDate birthDate;
    @NotNull(message = "Gender must be specified")
    private Gender gender;
    @NotNull(message = "Group cannot be empty")
    private UUID groupId;
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;
}
