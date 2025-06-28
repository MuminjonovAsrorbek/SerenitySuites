package uz.dev.serenitysuites.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import uz.dev.serenitysuites.entity.template.AbsLongEntity;
import uz.dev.serenitysuites.enums.RoomStatus;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 13:00
 **/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@FieldNameConstants
public class Room extends AbsLongEntity {

    @Column(nullable = false)
    private String roomNumber;

    @ManyToOne
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    private RoomStatus status;

    @Version
    private Long version;

    @OneToMany(mappedBy = "room")
    @ToString.Exclude
    private List<Reservation> reservations;
}
