package smartlms.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import smartlms.dto.request.StudentProfileRequestDto;
import smartlms.dto.response.PageResponse;
import smartlms.dto.response.StudentProfileResponseDto;
import smartlms.entity.GroupEntity;
import smartlms.entity.StudentProfileEntity;
import smartlms.exception.DataNotFoundException;
import smartlms.exception.UnauthorizedException;
import smartlms.mapper.StudentProfileMapper;
import smartlms.repository.GroupRepository;
import smartlms.repository.StudentProfileRepository;
import smartlms.service.StudentProfileService;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final StudentProfileMapper studentProfileMapper;
    private final GroupRepository groupRepository;

    @Override
    public StudentProfileResponseDto getMyStudentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("User not authenticated or not logged in");
            throw new UnauthorizedException("User not authenticated or not logged in");
        }
        String username = authentication.getName();
        StudentProfileEntity studentProfileEntity = studentProfileRepository.findByUser_Username(username).orElseThrow(() -> {
            log.warn("Student Profile not found with username: {}", username);
            return new DataNotFoundException("Student Profile not found with username: " + username);
        });
        log.info("Getting current student profile with username: {}", username);
        return studentProfileMapper.toDto(studentProfileEntity);
    }

    @Override
    public PageResponse<StudentProfileResponseDto> getStudentsProfileByGroupId(UUID groupId, Pageable pageable) {
       GroupEntity groupEntity = groupRepository.findById(groupId).orElseThrow(() -> {
            log.warn("Group not found with id: {}", groupId);
            return new DataNotFoundException("Group not found with id: " + groupId);
        });
       Page<StudentProfileEntity> entityPage = studentProfileRepository.findByGroup(groupEntity, pageable);
       Page<StudentProfileResponseDto> responseDtoPage = entityPage.map(studentProfileMapper::toDto);
       return PageResponse.<StudentProfileResponseDto>builder()
               .content(responseDtoPage.getContent())
               .page(pageable.getPageNumber())
               .size(responseDtoPage.getSize())
               .totalElements(responseDtoPage.getTotalElements())
               .totalPages(responseDtoPage.getTotalPages())
               .last(responseDtoPage.isLast())
               .build();
    }

    @Override
    public void updateStudentProfileById(UUID studentId, StudentProfileRequestDto studentProfileRequestDto) {
        StudentProfileEntity studentProfileEntity = studentProfileRepository.findById(studentId).orElseThrow(() -> {
            log.warn("Student Profile not found with id: {}", studentId);
            return new DataNotFoundException("Student Profile not found with id: " + studentId);
        });
        studentProfileRepository.save(studentProfileMapper.toEntity(studentProfileEntity, studentProfileRequestDto));
        log.info("Updating current student profile with id: {}", studentId);
    }

}
