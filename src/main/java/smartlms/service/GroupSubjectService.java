package smartlms.service;


import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smartlms.dto.request.GroupSubjectRequestDto;
import smartlms.dto.response.GroupSubjectResponseDtoForAdmin;
import smartlms.dto.response.GroupSubjectResponseDtoForStudents;
import smartlms.dto.response.GroupSubjectResponseDtoForTeachers;
import smartlms.dto.response.PageResponse;

import java.util.UUID;

@Service
public interface GroupSubjectService {
    void createGroupSubject(GroupSubjectRequestDto groupSubjectRequestDto);
    PageResponse<GroupSubjectResponseDtoForStudents> getMyGroupSubjectForStudents(Integer semester, Pageable pageable);
    PageResponse<GroupSubjectResponseDtoForTeachers> findMyGroupsForTeachers(Integer semester, Pageable pageable);
    PageResponse<GroupSubjectResponseDtoForAdmin> findAllGroupSubjectsByGroupId(UUID groupId, Pageable pageable);
}
