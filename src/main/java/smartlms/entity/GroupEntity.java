package smartlms.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupEntity extends BaseEntity{

    @Column(nullable = false, unique = true, length = 50)
    private String name; // 214-22 Kli

    @Column(nullable = false)
    private Integer course; // 1-4

    @Column(length = 100)
    private String faculty;
}
