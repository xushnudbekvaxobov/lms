package smartlms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartlms.entity.GroupEntity;
import smartlms.entity.StudentProfileEntity;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfileEntity, UUID> {

    Optional<StudentProfileEntity> findByUser_Username(String username);

    Page<StudentProfileEntity> findByGroup(GroupEntity group, Pageable pageable);
}
