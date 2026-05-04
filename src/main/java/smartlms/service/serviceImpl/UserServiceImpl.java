package smartlms.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import smartlms.dto.request.*;
import smartlms.dto.response.*;
import smartlms.entity.GroupEntity;
import smartlms.entity.UserEntity;
import smartlms.entity.enums.UserRole;
import smartlms.entity.enums.UserStatus;
import smartlms.exception.AlreadyExistsException;
import smartlms.exception.DataNotFoundException;
import smartlms.exception.UnauthorizedException;
import smartlms.jwt.JwtService;
import smartlms.mapper.StudentProfileMapper;
import smartlms.mapper.TeacherProfileMapper;
import smartlms.mapper.UserMapper;
import smartlms.repository.GroupRepository;
import smartlms.repository.StudentProfileRepository;
import smartlms.repository.TeacherProfileRepository;
import smartlms.repository.UserRepository;
import smartlms.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TeacherProfileMapper teacherProfileMapper;
    private final TeacherProfileRepository teacherProfileRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final StudentProfileMapper studentProfileMapper;
    private final GroupRepository groupRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    @Override
    public void registerTeacher(TeacherCreateDto teacherCreateDto) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(teacherCreateDto.getUsername());
        if (userEntity.isPresent()) {
            log.warn("Already registered user with this username: {}", teacherCreateDto.getUsername());
            throw new AlreadyExistsException("Username already exists");
        }
        UserEntity savedUser = userRepository.save(userMapper.toEntity(teacherCreateDto.getUsername(), passwordEncoder.encode(teacherCreateDto.getPassword()), UserRole.TEACHER));
        teacherProfileRepository.save(teacherProfileMapper.toEntity(savedUser, teacherCreateDto.getFirstName(), teacherCreateDto.getLastName(), teacherCreateDto.getGender(), teacherCreateDto.getBirthDate()));
        log.info("Teacher registered successfully with username: {}", teacherCreateDto.getUsername());
    }

    @Transactional
    @Override
    public void registerStudent(StudentCreateDto studentCreateDto) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(studentCreateDto.getUsername());
        if (userEntity.isPresent()) {
            log.warn("Already registered student with this username: {}", studentCreateDto.getUsername());
            throw new AlreadyExistsException("Username already exists");
        }
        GroupEntity groupEntity = groupRepository.findById(studentCreateDto.getGroupId()).orElseThrow(() -> {
            log.warn("Group not found with id: {}", studentCreateDto.getGroupId());
            return new DataNotFoundException("Group not found");
        });
        UserEntity savedUser = userRepository.save(userMapper.toEntity(studentCreateDto.getUsername(), passwordEncoder.encode(studentCreateDto.getPassword()), UserRole.STUDENT));
        studentProfileRepository.save(studentProfileMapper.toEntity(savedUser, groupEntity, studentCreateDto.getFirstName(), studentCreateDto.getLastName(), studentCreateDto.getGender(), studentCreateDto.getBirthDate()));
        log.info("Student registered successfully with username: {}", studentCreateDto.getUsername());
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
       UserEntity userEntity = userRepository.findByUsername(loginRequestDto.getUsername()).orElseThrow(()-> new UnauthorizedException("User not registered"));
           if (userEntity.getStatus() != UserStatus.ACTIVE) {
               log.warn("User with username: {} is not active", loginRequestDto.getUsername());
               throw new UnauthorizedException("User is not active");
           }
           if (!passwordEncoder.matches(loginRequestDto.getPassword(), userEntity.getPassword())) {
               log.warn("User with username: {} has incorrect password", loginRequestDto.getUsername());
               throw  new UnauthorizedException("User password is incorrect");
           }
           log.info("User logged in successfully with username: {}", loginRequestDto.getUsername());
           return new LoginResponseDto(jwtService.generateAccessToken(userEntity), jwtService.generateRefreshToken(userEntity));
    }

    @Override
    public void changeMyPassword(ChangePasswordRequestDto changePasswordDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            log.warn("User not authenticated");
            throw new UnauthorizedException("User not authenticated");
        }
        String username = authentication.getName();
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found with username: {}", username);
            return new DataNotFoundException("User not found");
        });
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), userEntity.getPassword())) {
            log.warn("Old password mismatch username: {}", userEntity.getUsername());
            throw new UnauthorizedException("Old password mismatch");
        }
        userEntity.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(userEntity);
        log.info("Password changed successfully with username: {}", userEntity.getUsername());
    }

    @Override
    public LoginResponseDto newAccessToken(String refreshToken) {
        if (!jwtService.isValid(refreshToken)) {
            log.warn("Refresh token is invalid");
            throw new UnauthorizedException("Refresh token is invalid");
        }
        String username = jwtService.getUsername(refreshToken);
        UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found with username: {}", username);
            return new DataNotFoundException("User not found or deleted");
        });
        return new LoginResponseDto(jwtService.generateAccessToken(userEntity), refreshToken);
    }
}