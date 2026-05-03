package smartlms.mapper;

import org.springframework.stereotype.Component;
import smartlms.dto.request.AssignmentRequestDto;
import smartlms.dto.response.AssignmentResponseDto;
import smartlms.entity.AssignmentEntity;
import smartlms.entity.GroupSubjectEntity;

@Component
public class AssignmentMapper {
    public AssignmentEntity toEntity(GroupSubjectEntity groupSubjectEntity, AssignmentRequestDto assignmentRequestDto){
        return AssignmentEntity.builder()
                .groupSubject(groupSubjectEntity)
                .title(assignmentRequestDto.getTitle())
                .dueDate(assignmentRequestDto.getDueDate())
                .maxScore(assignmentRequestDto.getMaxScore())
                .build();
    }

    public AssignmentResponseDto toDto(AssignmentEntity assignmentEntity){
        return AssignmentResponseDto.builder()
                .id(assignmentEntity.getId())
                .title(assignmentEntity.getTitle())
                .dueDate(assignmentEntity.getDueDate())
                .maxScore(assignmentEntity.getMaxScore())
                .build();
    }
}
