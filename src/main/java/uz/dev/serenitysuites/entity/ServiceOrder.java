package uz.dev.serenitysuites.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.serenitysuites.entity.template.AbsLongEntity;

import java.time.LocalDateTime;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 13:06
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class ServiceOrder extends AbsLongEntity {

    @ManyToOne
    private Reservation reservation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private LocalDateTime orderTime;

}
