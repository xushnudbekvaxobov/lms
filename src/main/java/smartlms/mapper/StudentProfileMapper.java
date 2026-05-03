package smartlms.mapper;

import org.springframework.stereotype.Component;
import smartlms.dto.request.StudentProfileRequestDto;
import smartlms.dto.response.StudentProfileResponseDto;
import smartlms.entity.GroupEntity;
import smartlms.entity.StudentProfileEntity;
import smartlms.entity.UserEntity;
import smartlms.entity.enums.Gender;

import java.time.LocalDate;

@Component
public class StudentProfileMapper {
    private final GroupMapper groupMapper;

    public StudentProfileMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    public StudentProfileEntity toEntity(UserEntity userEntity, GroupEntity groupEntity, String firstName, String lastName, Gender gender, LocalDate birthDate) {
        return StudentProfileEntity.builder()
                .user(userEntity)
                .group(groupEntity)
                .firstName(firstName)
                .lastName(lastName)
                .gender(gender)
                .birthDate(birthDate)
                .build();
    }
    public StudentProfileEntity toEntity(StudentProfileEntity studentProfileEntity, StudentProfileRequestDto studentProfileRequestDto) {
        studentProfileEntity.setFirstName(studentProfileRequestDto.getFirstName());
        studentProfileEntity.setLastName(studentProfileRequestDto.getLastName());
        studentProfileEntity.setGender(studentProfileRequestDto.getGender());
        studentProfileEntity.setBirthDate(studentProfileRequestDto.getBirthDate());
        return studentProfileEntity;
    }
    public StudentProfileResponseDto toDto(StudentProfileEntity studentProfileEntity) {
        return StudentProfileResponseDto.builder()
                .Id(studentProfileEntity.getId())
                .firstName(studentProfileEntity.getFirstName())
                .lastName(studentProfileEntity.getLastName())
                .birthDate(studentProfileEntity.getBirthDate())
                .gender(studentProfileEntity.getGender())
                .group(groupMapper.toDto(studentProfileEntity.getGroup()))
                .build();
    }
}
