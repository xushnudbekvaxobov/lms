package smartlms.dto.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AssigmentProjectionForStudents {
    String getTitle();
    LocalDateTime getDueDate();
    Double getMaxScore();
    UUID getTeacherId();
    String getTeacherFirstName();
    String getTeacherLastName();
    UUID getSubjectId();
    String getSubjectName();
    String getSubjectCode();
    UUID getGradeId();
    Double getScore();
}
