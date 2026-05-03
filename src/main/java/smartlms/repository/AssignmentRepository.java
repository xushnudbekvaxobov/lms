package smartlms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartlms.dto.projection.AssigmentProjectionForStudents;
import smartlms.entity.AssignmentEntity;
import java.util.List;
import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentEntity, UUID> {
    @Query(value = """
              SELECT asg.title AS title,
                     asg.dueDate AS dueDate,
                     asg.maxScore AS maxScore,
                     t.id AS teacherId,
                     t.firstName AS teacherFirstName,
                     t.lastName AS teacherLastName,
                     s.id AS subjectId,
                     s.name AS subjectName,
                     s.code AS subjectCode,
                     grd.id AS gradeId,
                     grd.score AS gradeScore
              FROM  AssignmentEntity asg
              JOIN asg.groupSubject gs
              JOIN gs.teacher t
              JOIN gs.subject s
              JOIN gs.group gr
              LEFT JOIN StudentProfileEntity st
              ON st.group.id = gr.id
              LEFT JOIN GradeEntity grd
              ON grd.assignment.id = asg.id
              AND grd.student.id = st.id
              WHERE asg.groupSubject.id = :groupSubjectId
              AND st.user.username = :username
              GROUP BY 
              asg.title, asg.dueDate, asg.maxScore, t.id, t.firstName, t.lastName, s.id, s.name, s.code, grd.id, grd.score
            """,
            countQuery = """
               SELECT COUNT(DISTINCT asg.id)
               FROM AssignmentEntity asg
               JOIN asg.groupSubject gs
               JOIN gs.group gr
               JOIN StudentProfileEntity st
               ON st.group.id = gr.id
               WHERE asg.groupSubject.id = :groupSubjectId
               AND st.user.username = :username
"""
    )
    Page<AssigmentProjectionForStudents> getMyAssignmentsForStudents(String username, UUID groupSubjectId, Pageable pageable);

    @Query("""
             SELECT coalesce(sum (a.maxScore)) AS finalScore
             FROM AssignmentEntity a
             WHERE a.groupSubject.id = :groupSubjectId
""")
    Double sumOfMaxScore(UUID groupSubjectId);


}
