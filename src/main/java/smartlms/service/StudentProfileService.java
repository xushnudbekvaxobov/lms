package smartlms.service;


import org.springframework.data.domain.Pageable;
import smartlms.dto.request.StudentProfileRequestDto;
import smartlms.dto.response.PageResponse;
import smartlms.dto.response.StudentProfileResponseDto;

import java.util.UUID;

public interface StudentProfileService {
    StudentProfileResponseDto getMyStudentProfile();
    PageResponse<StudentProfileResponseDto> getStudentsProfileByGroupId(UUID groupId, Pageable  pageable);
    void updateStudentProfileById(UUID studentId, StudentProfileRequestDto studentProfileRequestDto);
}
