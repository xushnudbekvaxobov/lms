package smartlms.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartlms.dto.request.GroupSubjectRequestDto;
import smartlms.dto.response.ApiResponse;
import smartlms.service.GroupSubjectService;

@RestController
@RequestMapping("/api/groups/subjects")
public class GroupSubjectController {

    private final GroupSubjectService groupSubjectService;

    public GroupSubjectController(GroupSubjectService groupSubjectService) {
        this.groupSubjectService = groupSubjectService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createGroupSubject(@RequestBody @Valid GroupSubjectRequestDto groupSubjectRequestDto) {
        groupSubjectService.createGroupSubject(groupSubjectRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Group subject created successfully", null, 201));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/students/my")
    public ResponseEntity<ApiResponse<?>>  getMyGroupSubjectForStudents(@RequestParam Integer semester,
                                                                        Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Group subject get successfully", groupSubjectService.getMyGroupSubjectForStudents(semester, pageable), 200));
    }

    @PreAuthorize("hasRole('TEACHER')")
    @GetMapping("/teachers/my")
    public ResponseEntity<ApiResponse<?>> getMyGroupSubjectForTeachers(@RequestParam Integer semester,
                                                                       Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Teachers groups getting successfully", groupSubjectService.findMyGroupsForTeachers(semester, pageable), 200));
    }


}