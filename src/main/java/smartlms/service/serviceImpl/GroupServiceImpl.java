package smartlms.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smartlms.dto.request.GroupRequestDto;
import smartlms.dto.response.GroupResponseDto;
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
    public PageResponse<GroupResponseDto> getAllGroup(Pageable pageable) {
        Page<GroupEntity> entityPage = groupRepository.findAll(pageable);
        Page<GroupResponseDto> responseDtoPage = entityPage.map(groupMapper::toDto);
        return PageResponse.<GroupResponseDto>builder()
                .content(responseDtoPage.getContent())
                .page(responseDtoPage.getNumber())
                .size(responseDtoPage.getSize())
                .totalElements(responseDtoPage.getTotalElements())
                .totalPages(responseDtoPage.getTotalPages())
                .last(responseDtoPage.isLast())
                .build();
    }

}
