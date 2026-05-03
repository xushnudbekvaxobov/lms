package smartlms.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartlms.dto.request.AssignmentRequestDto;
import smartlms.dto.response.ApiResponse;
import smartlms.service.AssignmentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/assignments")
public class AssigmentController {

    private final AssignmentService assignmentService;

    public AssigmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/students/my/{groupSubjectId}")
    public ResponseEntity<ApiResponse<?>> getMyAssignmentsForStudents(@PathVariable UUID groupSubjectId,
                                                                      Pageable pageable) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Assignments retrieved successfully", assignmentService.getMyAssignmentsForStudents(groupSubjectId, pageable), 200));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/{groupSubjectId}")
    public ResponseEntity<ApiResponse<?>> createAssignment(@PathVariable UUID groupSubjectId, @RequestBody AssignmentRequestDto assignmentRequestDto) {
       assignmentService.createAssignments(groupSubjectId, assignmentRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Assignment created successfully", null, 201));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/teachers/my/{groupSubjectId}")
    public ResponseEntity<ApiResponse<?>> getMyAssignmentsForTeachers(@PathVariable UUID groupSubjectId) {
        return ResponseEntity
                .ok(new ApiResponse<>(true, "Assignments retrieved successfully", assignmentService.getMyAssignmentsForTeachers(groupSubjectId), 200));
    }
}
