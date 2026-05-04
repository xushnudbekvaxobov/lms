package smartlms.service;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smartlms.dto.request.GroupRequestDto;
import smartlms.dto.response.GroupResponseDto;
import smartlms.dto.response.GroupResponseDtoForAdmin;
import smartlms.dto.response.PageResponse;

@Service
public interface GroupService {
    void addGroup(GroupRequestDto groupRequestDto);
    PageResponse<GroupResponseDtoForAdmin> getAllGroup(Pageable pageable);
}
