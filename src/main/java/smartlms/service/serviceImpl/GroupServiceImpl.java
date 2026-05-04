package smartlms.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smartlms.dto.projection.GroupProjectionForAdmin;
import smartlms.dto.request.GroupRequestDto;
import smartlms.dto.response.GroupResponseDto;
import smartlms.dto.response.GroupResponseDtoForAdmin;
import smartlms.dto.response.PageResponse;
import smartlms.entity.GroupEntity;
import smartlms.exception.AlreadyExistsException;
import smartlms.mapper.GroupMapper;
import smartlms.repository.GroupRepository;
import smartlms.service.GroupService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    @Override
    public void addGroup(GroupRequestDto groupRequestDto) {
        Optional<GroupEntity> groupEntity = groupRepository.findByName(groupRequestDto.getName());
        if (groupEntity.isPresent()) {
            log.warn("Group with name {} already exists", groupRequestDto.getName());
            throw  new AlreadyExistsException("Group already exists with name: " + groupRequestDto.getName());
        }
        groupRepository.save(groupMapper.toEntity(groupRequestDto));
        log.info("Group has been added with name {} ", groupRequestDto.getName());
    }

    @Override
    public PageResponse<GroupResponseDtoForAdmin> getAllGroup(Pageable pageable) {
        Page<GroupProjectionForAdmin> entityPage = groupRepository.findAllProjectedBy(pageable);
        Page<GroupResponseDtoForAdmin> responseDtoPage = entityPage.map(projection -> {
            GroupResponseDto group = new GroupResponseDto(
                    projection.getGroupId(),
                    projection.getGroupName(),
                    projection.getGroupCourse(),
                    projection.getGroupFaculty()
            );
            return new GroupResponseDtoForAdmin(
                    projection.getStudentCount(),
                    projection.getSubjectCount(),
                    group
            );
        });
        return new PageResponse<>(
                responseDtoPage.getContent(),
                responseDtoPage.getNumber(),
                responseDtoPage.getSize(),
                responseDtoPage.getTotalElements(),
                responseDtoPage.getTotalPages(),
                responseDtoPage.isLast()
        );}

}
