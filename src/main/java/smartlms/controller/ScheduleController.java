package smartlms.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smartlms.dto.request.ScheduleRequestDto;
import smartlms.dto.response.ApiResponse;
import smartlms.service.ScheduleService;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createSchedule(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        scheduleService.createSchedule(scheduleRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Schedule created successfully", null, 201));
    }
}

