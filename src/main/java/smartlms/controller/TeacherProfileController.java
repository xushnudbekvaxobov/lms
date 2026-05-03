package smartlms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartlms.dto.request.TeacherProfileRequestDto;
import smartlms.dto.response.ApiResponse;
import smartlms.dto.response.PageResponse;
import smartlms.dto.response.TeacherProfileResponseDto;
import smartlms.service.TeacherProfileService;

import java.util.UUID;

@RestController
@RequestMapping("/api/profile/teachers")
@RequiredArgsConstructor
public class TeacherProfileController {

    private final TeacherProfileService teacherProfileService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<?>> getMyProfile() {
        TeacherProfileResponseDto profile = teacherProfileService.getCurrentTeacherProfile();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Profile retrieved successfully", profile, 200));
    }

    @PutMapping("/{teacherId}")
    public ResponseEntity<ApiResponse<?>> updateMyProfile(@PathVariable UUID teacherId, @RequestBody @Valid TeacherProfileRequestDto requestDto) {
        teacherProfileService.updateTeacherProfileById(teacherId, requestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Profile updated successfully", null, 200));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllProfiles(Pageable pageable) {
        PageResponse<?> profiles = teacherProfileService.getTeacherProfiles(pageable);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Profiles retrieved successfully", profiles, 200));
    }
}