package smartlms.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import smartlms.dto.request.TeacherProfileRequestDto;
import smartlms.dto.response.PageResponse;
import smartlms.dto.response.TeacherProfileResponseDto;
import smartlms.entity.TeacherProfileEntity;
import smartlms.exception.DataNotFoundException;
import smartlms.exception.UnauthorizedException;
import smartlms.mapper.TeacherProfileMapper;
import smartlms.repository.TeacherProfileRepository;
import smartlms.service.TeacherProfileService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherProfileServiceImpl implements TeacherProfileService {

    private final TeacherProfileRepository teacherProfileRepository;
    private final TeacherProfileMapper teacherProfileMapper;

    @Override
    public TeacherProfileResponseDto getCurrentTeacherProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("User not authenticated or not logged in");
            throw new UnauthorizedException("User not authenticated or not logged in");
        }
        String username = authentication.getName();
        TeacherProfileEntity teacherProfileEntity = teacherProfileRepository.findByUser_Username(username).orElseThrow(() -> {
            log.warn("Teacher Profile not found with username: {}", username);
            return new DataNotFoundException("Teacher Profile not found with username: " + username);
        });
        log.info("Getting current teacher profile with username: {}", username);
        return teacherProfileMapper.toDto(teacherProfileEntity);
    }


    @Override
    public void updateTeacherProfileById(UUID teacherId, TeacherProfileRequestDto teacherProfileRequestDto) {
       TeacherProfileEntity teacherProfileEntity = teacherProfileRepository.findById(teacherId).orElseThrow(() -> {
                   log.warn("Teacher Profile not found with id: {}", teacherId);
                   return new DataNotFoundException("Teacher Profile not found with id: " + teacherId);
               });
       log.info("Updating current student profile with id: {}", teacherId);
        teacherProfileRepository.save(teacherProfileMapper.toEntity(teacherProfileEntity, teacherProfileRequestDto));
    }

    @Override
    public PageResponse<?> getTeacherProfiles(Pageable pageable) {
        Page<TeacherProfileEntity> entityPage = teacherProfileRepository.findAll(pageable);
        Page<TeacherProfileResponseDto> responseDtoPage = entityPage.map(teacherProfileMapper::toDto);
       return PageResponse.<TeacherProfileResponseDto>builder()
               .content(responseDtoPage.getContent())
               .page(responseDtoPage.getNumber())
               .size(responseDtoPage.getSize())
               .totalElements(responseDtoPage.getTotalElements())
               .totalPages(responseDtoPage.getTotalPages())
               .last(responseDtoPage.isLast())
               .build();
    }
}
