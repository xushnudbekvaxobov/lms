package smartlms.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smartlms.dto.request.GroupRequestDto;
import smartlms.dto.response.ApiResponse;
import smartlms.service.GroupService;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<?>> createGroup(@RequestBody GroupRequestDto groupRequestDto) {
        groupService.addGroup(groupRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Group created successfully", null, 201));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllGroups(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ApiResponse<>(true, "Getting all groups by pageable", groupService.getAllGroup(pageable), 200));
    }

}
