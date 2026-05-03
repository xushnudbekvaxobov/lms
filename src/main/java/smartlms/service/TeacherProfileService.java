package smartlms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smartlms.dto.request.TeacherProfileRequestDto;
import smartlms.dto.response.PageResponse;
import smartlms.dto.response.TeacherProfileResponseDto;

import java.util.UUID;

@Service
public interface TeacherProfileService {
    public TeacherProfileResponseDto getCurrentTeacherProfile();
    void updateTeacherProfileById(UUID teacherId, TeacherProfileRequestDto teacherProfileRequestDto);
    PageResponse<?> getTeacherProfiles(Pageable pageable);
}
