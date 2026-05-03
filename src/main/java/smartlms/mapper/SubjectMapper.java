package smartlms.mapper;

import org.springframework.stereotype.Component;
import smartlms.dto.request.SubjectRequestDto;
import smartlms.dto.response.SubjectResponseDto;
import smartlms.entity.SubjectEntity;

@Component
public class SubjectMapper {
    public SubjectEntity toEntity(SubjectRequestDto subjectRequestDto) {
        return SubjectEntity.builder()
                .name(subjectRequestDto.getName())
                .code(subjectRequestDto.getCode())
                .build();
    }
    public SubjectResponseDto  toDto(SubjectEntity subjectEntity) {
        return SubjectResponseDto.builder()
                .id(subjectEntity.getId())
                .name(subjectEntity.getName())
                .code(subjectEntity.getCode())
                .build();
    }
}
