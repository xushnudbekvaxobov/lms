package smartlms.service;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smartlms.dto.request.SubjectRequestDto;
import smartlms.dto.response.PageResponse;
import smartlms.dto.response.SubjectResponseDto;

@Service
public interface SubjectService {
        void createSubject(SubjectRequestDto  subjectRequestDto);
        PageResponse<SubjectResponseDto> getAllSubjects(Pageable  pageable);
}
