package smartlms.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartlms.dto.request.SubjectRequestDto;
import smartlms.dto.response.ApiResponse;
import smartlms.service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createSubject(@RequestBody @Valid SubjectRequestDto subjectRequestDto) {
        subjectService.createSubject(subjectRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Subject created successfully", null, 201));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllSubjects(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Getting all subjects by pageable", subjectService.getAllSubjects(pageable), 200));
    }

}