package smartlms.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import smartlms.dto.request.SubjectRequestDto;
import smartlms.dto.response.PageResponse;
import smartlms.dto.response.SubjectResponseDto;
import smartlms.entity.SubjectEntity;
import smartlms.exception.AlreadyExistsException;
import smartlms.mapper.SubjectMapper;
import smartlms.repository.SubjectRepository;
import smartlms.service.SubjectService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public void createSubject(SubjectRequestDto subjectRequestDto) {
        Optional<SubjectEntity> subjectEntity = subjectRepository.findByName(subjectRequestDto.getName());
        if (subjectEntity.isPresent()) {
            log.warn("Subject already exists with name {}", subjectRequestDto.getName());
            throw  new AlreadyExistsException("Subject already exists with name " + subjectRequestDto.getName());
        }
        log.info("Creating Subject with name {}", subjectRequestDto.getName());
        subjectRepository.save(subjectMapper.toEntity(subjectRequestDto));
    }

    @Override
    public PageResponse<SubjectResponseDto> getAllSubjects(Pageable pageable) {
        Page<SubjectEntity> entityPage = subjectRepository.findAll(pageable);
        Page<SubjectResponseDto> responseDtoPage = entityPage.map(subjectMapper::toDto);
        return PageResponse.<SubjectResponseDto>builder()
                .content(responseDtoPage.getContent())
                .page(pageable.getPageNumber())
                .size(pageable.getPageSize())
                .totalElements(entityPage.getTotalElements())
                .totalPages(entityPage.getTotalPages())
                .last(entityPage.isLast())
                .build();
    }
}
