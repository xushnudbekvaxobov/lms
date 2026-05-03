package smartlms.dto.projection;

import java.time.LocalDateTime;

public interface AssignmentProjectionForTeachers {
    String getTitle();
    LocalDateTime getDueDate();
    Double getMaxScore();
}
