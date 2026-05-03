package smartlms.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import smartlms.dto.projection.GroupSubjectProjectionForStudents;
import smartlms.dto.projection.GroupSubjectProjectionForTeachers;
import smartlms.dto.request.GroupSubjectRequestDto;
import smartlms.dto.response.*;
import smartlms.entity.GroupEntity;
import smartlms.entity.GroupSubjectEntity;
import smartlms.entity.SubjectEntity;
import smartlms.entity.TeacherProfileEntity;
import smartlms.exception.AlreadyExistsException;
import smartlms.exception.DataNotFoundException;
import smartlms.repository.GroupRepository;
import smartlms.repository.GroupSubjectRepository;
import smartlms.repository.SubjectRepository;
import smartlms.repository.TeacherProfileRepository;
import smartlms.service.GroupSubjectService;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GroupSubjectServiceImpl implements GroupSubjectService {

    private final GroupRepository groupRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherProfileRepository teacherProfileRepository;
    private final GroupSubjectRepository groupSubjectRepository;

    @Override
    public void createGroupSubject(GroupSubjectRequestDto groupSubjectRequestDto) {
        GroupEntity groupEntity = groupRepository.findById(groupSubjectRequestDto.getGroupId()).orElseThrow(() -> {
            log.warn("Group not found with id {}", groupSubjectRequestDto.getGroupId());
            return new DataNotFoundException("Group not found");
        });
        SubjectEntity subjectEntity = subjectRepository.findById(groupSubjectRequestDto.getSubjectId()).orElseThrow(() -> {
            log.warn("Subject not found with id {}", groupSubjectRequestDto.getSubjectId());
            return new DataNotFoundException("Subject not found");
        });
        TeacherProfileEntity teacherProfileEntity = teacherProfileRepository.findById(groupSubjectRequestDto.getTeacherId()).orElseThrow(() -> {
            log.warn("Teacher not found with id {}", groupSubjectRequestDto.getTeacherId());
            return new DataNotFoundException("Teacher not found");
        });
        boolean exists = groupSubjectRepository.existsByGroup_IdAndSubject_IdAndSemesterAndAcademicYear(groupSubjectRequestDto.getGroupId(), groupSubjectRequestDto.getSubjectId(), groupSubjectRequestDto.getSemester(), groupSubjectRequestDto.getAcademicYear());
        if (exists) {
            log.warn("Group subject already exists. groupId={}, subjectId={}, semester={}, academicYear={}", groupSubjectRequestDto.getSubjectId(), groupSubjectRequestDto.getAcademicYear(), groupSubjectRequestDto.getSemester(), groupSubjectRequestDto.getGroupId());
            throw new AlreadyExistsException("This subject is already assigned to the group for this semester and academic year");
        }
        log.info("Creating group subject with groupId={}, subjectId={}, teacherId={}, semester={}, academicYear={}", groupSubjectRequestDto.getGroupId(), groupSubjectRequestDto.getSubjectId(), groupSubjectRequestDto.getTeacherId(), groupSubjectRequestDto.getSemester(), groupSubjectRequestDto.getAcademicYear());
        GroupSubjectEntity groupSubjectEntity = GroupSubjectEntity.builder().group(groupEntity).subject(subjectEntity).teacher(teacherProfileEntity).semester(groupSubjectRequestDto.getSemester()).academicYear(groupSubjectRequestDto.getAcademicYear()).finalScore(groupSubjectRequestDto.getFinalScore()).build();
        groupSubjectRepository.save(groupSubjectEntity);
    }

    @Override
    public PageResponse<GroupSubjectResponseDtoForStudents> getMyGroupSubjectForStudents(Integer semester, Pageable pageable) {
        if (semester != 1 && semester != 2) {
            throw new DataNotFoundException("Semester might be between 1 and 2");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Page<GroupSubjectProjectionForStudents> projectionPage = groupSubjectRepository.findALlByStudentId(username, semester, pageable);
        Page<GroupSubjectResponseDtoForStudents> responseDtoPage = projectionPage.map(projection -> {
            SubjectResponseDto subject = new SubjectResponseDto(
                    projection.getSubjectId(),
                    projection.getSubjectName(),
                    projection.getSubjectCode()
            );
            TeacherResponseDtoForStudents teacher = new TeacherResponseDtoForStudents(
                    projection.getTeacherId(),
                    projection.getTeacherFirstName(),
                    projection.getTeacherLastName()
            );

            return new GroupSubjectResponseDtoForStudents(
              projection.getId(),
              projection.getSemester(),
              projection.getNbCount(),
              subject,
              teacher
            );
        });
        return new PageResponse<>(
                responseDtoPage.getContent(),
                responseDtoPage.getNumber(),
                responseDtoPage.getSize(),
                responseDtoPage.getTotalElements(),
                responseDtoPage.getTotalPages(),
                responseDtoPage.isLast()
        );

    }

    @Override
    public PageResponse<GroupSubjectResponseDtoForTeachers> findMyGroupsForTeachers(Integer semester, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Finding groups for teacher with username {} and semester {}", username, semester);
        Page<GroupSubjectProjectionForTeachers> projectionPage = groupSubjectRepository.findAllByTeacherUsername(username, semester, pageable);
        Page<GroupSubjectResponseDtoForTeachers> responsePage =   projectionPage.map(projection -> {
            GroupResponseDto group = new GroupResponseDto(
                    projection.getGroupId(),
                    projection.getGroupName(),
                    projection.getGroupCourse(),
                    projection.getGroupFaculty()
            );
            SubjectResponseDto subject = new SubjectResponseDto(
                    projection.getSubjectId(),
                    projection.getSubjectName(),
                    projection.getSubjectCode()
            );
            return new GroupSubjectResponseDtoForTeachers(
                    projection.getStudentCount(),
                    group,
                    subject
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
}
