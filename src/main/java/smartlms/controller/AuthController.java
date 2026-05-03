package smartlms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartlms.dto.request.ChangePasswordRequestDto;
import smartlms.dto.request.LoginRequestDto;
import smartlms.dto.request.StudentCreateDto;
import smartlms.dto.request.TeacherCreateDto;
import smartlms.dto.response.ApiResponse;
import smartlms.service.UserService;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/student")
    public ResponseEntity<ApiResponse<?>> registerStudent(@RequestBody @Valid StudentCreateDto  studentCreateDto) {
        userService.registerStudent(studentCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Student registered successfully", null, 201));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register/teacher")
    public ResponseEntity<ApiResponse<?>> registerTeacher(@RequestBody @Valid TeacherCreateDto teacherCreateDto) {
        userService.registerTeacher(teacherCreateDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Teacher registered successfully", null, 201));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody @Valid LoginRequestDto loginRequestDto){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Login successful", userService.login(loginRequestDto), 200));
    }
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER', 'STUDENT')")
    @PatchMapping("/me/change-password")
    public ResponseEntity<ApiResponse<?>> changePassword(@RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto){
        userService.changeMyPassword(changePasswordRequestDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Password changed successfully", null, 200));
    }

    @PostMapping("/refresh-token/{refreshToken}")
    public ResponseEntity<ApiResponse<?>> refreshToken(@PathVariable String refreshToken){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Token refreshed successfully", userService.newAccessToken(refreshToken), 200));
    }

}