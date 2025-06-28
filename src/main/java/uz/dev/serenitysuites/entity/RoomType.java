package uz.dev.serenitysuites.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.serenitysuites.entity.template.AbsLongEntity;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 12:59
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class RoomType extends AbsLongEntity {

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long basePrice;

}
