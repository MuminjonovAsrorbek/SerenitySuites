package uz.dev.serenitysuites.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.serenitysuites.entity.template.AbsLongEntity;
import uz.dev.serenitysuites.enums.TaskStatus;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 13:07
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class HousekeepingLog extends AbsLongEntity {

    @ManyToOne
    private Room room;

    @ManyToOne
    private User staff;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime startedAt;

    private LocalDateTime completedAt;

}
