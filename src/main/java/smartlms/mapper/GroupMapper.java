package smartlms.mapper;

import org.springframework.stereotype.Component;
import smartlms.dto.request.GroupRequestDto;
import smartlms.dto.response.GroupResponseDto;
import smartlms.entity.GroupEntity;

@Component
public class GroupMapper {
    public GroupEntity toEntity(GroupRequestDto groupRequestDto) {
        return GroupEntity.builder()
                .name(groupRequestDto.getName())
                .course(groupRequestDto.getCourse())
                .faculty(groupRequestDto.getFaculty())
                .build();
    }
    public GroupResponseDto toDto(GroupEntity groupEntity) {
        return GroupResponseDto.builder()
                .id(groupEntity.getId())
                .name(groupEntity.getName())
                .course(groupEntity.getCourse())
                .faculty(groupEntity.getFaculty())
                .build();
    }
}
