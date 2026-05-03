package smartlms.service;

import org.springframework.stereotype.Service;
import smartlms.dto.request.*;
import smartlms.dto.response.*;

@Service
public interface UserService {
    void registerTeacher(TeacherCreateDto teacherCreateDto);
    void registerStudent(StudentCreateDto studentCreateDto);
    LoginResponseDto login(LoginRequestDto loginRequestDto);
    void changeMyPassword(ChangePasswordRequestDto changePasswordDto);
    LoginResponseDto newAccessToken(String refreshToken);
}