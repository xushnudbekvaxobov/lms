package smartlms.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import smartlms.dto.projection.AssigmentProjectionForStudents;
import smartlms.dto.request.AssignmentRequestDto;
import smartlms.dto.response.*;
import smartlms.entity.AssignmentEntity;
import smartlms.entity.GroupSubjectEntity;
import smartlms.exception.DataNotFoundException;
import smartlms.exception.ScoreExceededException;
import smartlms.mapper.AssignmentMapper;
import smartlms.repository.AssignmentRepository;
import smartlms.repository.GroupRepository;
import smartlms.repository.GroupSubjectRepository;
import smartlms.service.AssignmentService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final GroupSubjectRepository groupSubjectRepository;
    private final AssignmentMapper assignmentMapper;
    private final GroupRepository groupRepository;

    @Override
    public PageResponse<AssignmentResponseDtoForStudents> getMyAssignmentsForStudents(UUID groupSubjectId, Pageable pageable) {
        Authentication  authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Page<AssigmentProjectionForStudents> projectionPage = assignmentRepository.getMyAssignmentsForStudents(username, groupSubjectId, pageable);
        Page<AssignmentResponseDtoForStudents> responsePage = projectionPage.map(projection ->{
            TeacherResponseDtoForStudents teacher = new TeacherResponseDtoForStudents(
                    projection.getTeacherId(),
                    projection.getTeacherFirstName(),
                    projection.getTeacherLastName()
            );
            SubjectResponseDto subject = new SubjectResponseDto(
                    projection.getSubjectId(),
                    projection.getSubjectName(),
                    projection.getSubjectCode()
            );
            Double score = 0.0;
            if (projection.getScore() != null){
                score = projection.getScore();
            }
            GradeResponseDto grade = new GradeResponseDto(
                    projection.getGradeId(),
                    score
            );
            return new AssignmentResponseDtoForStudents(
                    projection.getTitle(),
                    projection.getDueDate(),
                    projection.getMaxScore(),
                    teacher,
                    subject,
                    grade
            );
        });
        return new PageResponse<>(
                responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getSize(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages(),
                responsePage.isLast()
                );
    }

    @Override
    public void createAssignments(UUID groupSubjectId, AssignmentRequestDto assignmentRequestDto){
        GroupSubjectEntity groupSubjectEntity = groupSubjectRepository.findById(groupSubjectId).orElseThrow(() -> {
                log.warn("Subject not found for group by id: {}", groupSubjectId);
            return new DataNotFoundException("Subject not found for group");
        });
        Double sumOfMaxScore = assignmentRepository.sumOfMaxScore(groupSubjectId);
        if (sumOfMaxScore == null){
            sumOfMaxScore = 0.0;
        }
        double ofScore = groupSubjectEntity.getFinalScore()-sumOfMaxScore;
        if (sumOfMaxScore + assignmentRequestDto.getMaxScore() > groupSubjectEntity.getFinalScore()){
            log.warn("Assignment scores sum overflow");
            throw  new ScoreExceededException("Assignment scores sum overflow, You can create score: " + ofScore);
        }
        assignmentRepository.save(assignmentMapper.toEntity(groupSubjectEntity, assignmentRequestDto));
    }

    @Override
    public List<AssignmentResponseDto> getMyAssignmentsForTeachers(UUID groupSubjectId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        GroupSubjectEntity groupSubjectEntity = groupSubjectRepository.findByIdAndTeacher_User_Username(groupSubjectId, username);
        if (groupSubjectEntity == null){
            return List.of();
        }
        List<AssignmentEntity> assignmentEntities = groupSubjectEntity.getAssignmentEntityList();
        return assignmentEntities.stream().map(assignmentMapper::toDto).toList();
    }


}
