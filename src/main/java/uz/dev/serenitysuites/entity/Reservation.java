package uz.dev.serenitysuites.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.serenitysuites.entity.template.AbsLongEntity;
import uz.dev.serenitysuites.enums.ReservationStatus;

import java.time.LocalDate;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 13:03
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Reservation extends AbsLongEntity {

    @ManyToOne
    private User guest;

    @ManyToOne
    private Room room;

    @ManyToOne
    private RoomType roomType;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private Long totalPrice;
}
