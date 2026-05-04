package smartlms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartlms.dto.projection.GroupProjectionForAdmin;
import smartlms.entity.GroupEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, UUID> {
    Optional<GroupEntity> findByName(String name);

    @Query(value = """
                SELECT g.id AS groupId,
                       g.name AS groupName,
                       g.course AS groupCourse,
                       g.faculty AS groupFaculty,
                       COUNT(DISTINCT s.id) AS studentCount,
                       COUNT(DISTINCT gs.id) AS subjectCount
                FROM GroupEntity g
                LEFT JOIN StudentProfileEntity s
                ON s.group.id = g.id
                LEFT JOIN GroupSubjectEntity gs
                ON gs.group.id = g.id
                GROUP BY g.id, g.name, g.course, g.faculty
"""
    )
    Page<GroupProjectionForAdmin> findAllProjectedBy(Pageable pageable);
}
