package smartlms.mapper;

import org.springframework.stereotype.Component;
import smartlms.dto.request.TeacherProfileRequestDto;
import smartlms.dto.response.TeacherProfileResponseDto;
import smartlms.entity.TeacherProfileEntity;
import smartlms.entity.UserEntity;
import smartlms.entity.enums.Gender;

import java.time.LocalDate;

@Component
public class TeacherProfileMapper {
    public TeacherProfileEntity toEntity(UserEntity userEntity, String firstName, String lastName, Gender gender, LocalDate birthDate) {
        return TeacherProfileEntity.builder()
                .user(userEntity)
                .firstName(firstName)
                .lastName(lastName)
                .birthDate(birthDate)
                .gender(gender)
                .build();
    }
    public TeacherProfileEntity toEntity(TeacherProfileEntity teacherProfileEntity, TeacherProfileRequestDto teacherProfileRequestDto) {
        teacherProfileEntity.setFirstName(teacherProfileRequestDto.getFirstName());
        teacherProfileEntity.setLastName(teacherProfileRequestDto.getLastName());
        teacherProfileEntity.setGender(teacherProfileRequestDto.getGender());
        teacherProfileEntity.setBirthDate(teacherProfileRequestDto.getBirthDate());
        return teacherProfileEntity;
    }


    public TeacherProfileResponseDto toDto(TeacherProfileEntity teacherProfileEntity) {
        return TeacherProfileResponseDto.builder()
                .id(teacherProfileEntity.getId())
                .firstName(teacherProfileEntity.getFirstName())
                .lastName(teacherProfileEntity.getLastName())
                .gender(teacherProfileEntity.getGender())
                .birthDate(teacherProfileEntity.getBirthDate())
                .build();
    }
}
