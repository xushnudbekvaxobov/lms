package smartlms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import smartlms.dto.projection.GroupSubjectProjectionForStudents;
import smartlms.dto.projection.GroupSubjectProjectionForTeachers;
import smartlms.entity.GroupSubjectEntity;
import java.util.List;
import java.util.UUID;

@Repository
public interface GroupSubjectRepository extends JpaRepository<GroupSubjectEntity, UUID> {
    boolean existsByGroup_IdAndSubject_IdAndSemesterAndAcademicYear(UUID groupId, UUID subjectId, Integer semester, String academicYear);

    @Query(value = """
    SELECT 
        gs.id AS id,
        gs.semester AS semester,
        s.id AS subjectId,
        s.name AS subjectName,
        s.code AS subjectCode,
        t.id AS teacherId,
        t.firstName AS teacherFirstName,
        t.lastName AS teacherLastName,
        COUNT(DISTINCT a.id) AS nbCount
    FROM GroupSubjectEntity gs
    JOIN gs.subject s
    JOIN gs.teacher t
    JOIN gs.group gr
    JOIN StudentProfileEntity st
    ON st.group.id = gr.id
    LEFT JOIN ScheduleEntity sch 
        ON sch.groupSubject.id = gs.id
    LEFT JOIN AttendanceEntity a
        ON a.schedule.id = sch.id
        AND a.status = 'NB' 
        AND a.student.id = s.id
    WHERE gs.semester = :semester
    AND st.user.username = :username
    GROUP BY 
        gs.id, gs.semester, s.id, s.name, s.code, t.id, t.firstName, t.lastName""",
        countQuery = """
                    SELECT COUNT(DISTINCT gs.id)
                    FROM GroupSubjectEntity gs
                    JOIN gs.group g
                    JOIN StudentProfileEntity st\s
                        ON st.group.id = g.id
                    WHERE gs.semester = :semester
                      AND st.user.username = :username
                """)
    Page<GroupSubjectProjectionForStudents> findALlByStudentId(String username, int semester, Pageable pageable);

    @Query(value = """
        SELECT 
             s.id AS subjectId,
             s.name AS subjectName,
             s.code AS subjectCode,
             gr.id AS groupId,
             gr.name AS groupName,
             gr.course AS groupCourse,
             gr.faculty AS groupFaculty,
             COUNT(DISTINCT st.id) AS studentCount
        FROM GroupSubjectEntity gs
        JOIN gs.teacher t
        JOIN gs.group gr
                JOIN gs.subject s
        LEFT JOIN StudentProfileEntity st
            ON st.group.id = gr.id
        WHERE t.user.username = :username
        AND gs.semester = :semester
        GROUP BY 
                s.id, s.name, s.code, gr.id, gr.name, gr.course, gr.faculty""",
            countQuery = """
        SELECT COUNT(DISTINCT gr.id)
        FROM GroupSubjectEntity gs
        JOIN gs.teacher t
        JOIN gs.group gr
        WHERE t.user.username = :username
        AND gs.semester = :semester
"""
    )
    Page<GroupSubjectProjectionForTeachers> findAllByTeacherUsername(String username, Integer semester, Pageable pageable);

    GroupSubjectEntity findByIdAndTeacher_User_Username(UUID groupSubjectId, String username);
}
