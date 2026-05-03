package smartlms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import smartlms.entity.TeacherProfileEntity;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TeacherProfileRepository extends JpaRepository<TeacherProfileEntity, UUID> {
    Optional<TeacherProfileEntity> findByUser_Username(String username);
    Page<TeacherProfileEntity> findAll(Pageable pageable);
}
