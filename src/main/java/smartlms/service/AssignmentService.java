package smartlms.service;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smartlms.dto.request.AssignmentRequestDto;
import smartlms.dto.response.AssignmentResponseDto;
import smartlms.dto.response.AssignmentResponseDtoForStudents;
import smartlms.dto.response.PageResponse;

import java.util.List;
import java.util.UUID;

@Service
public interface AssignmentService {
    PageResponse<AssignmentResponseDtoForStudents> getMyAssignmentsForStudents(UUID groupSubjectId, Pageable pageable);     public void createAssignments(UUID groupSubjectId, AssignmentRequestDto assignmentRequestDto);
    List<AssignmentResponseDto> getMyAssignmentsForTeachers(UUID groupSubjectId);
}
