package smartlms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartlms.dto.request.StudentProfileRequestDto;
import smartlms.dto.response.ApiResponse;
import smartlms.service.StudentProfileService;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile/students")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getMyProfile() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Profile retrieved successfully", studentProfileService.getMyStudentProfile(), 200));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/group/{groupId}")
    public ResponseEntity<ApiResponse<?>> getStudentsByGroupId(@PathVariable UUID groupId, Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Getting Students profile", studentProfileService.getStudentsProfileByGroupId(groupId, pageable), 200));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{studentId}")
    public ResponseEntity<ApiResponse<?>> updateStudentProfile(@PathVariable UUID studentId,
                                                               @RequestBody @Valid StudentProfileRequestDto studentProfileRequestDto) {
        studentProfileService.updateStudentProfileById(studentId, studentProfileRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "User profile updated successfully", null, 200));
    }

}