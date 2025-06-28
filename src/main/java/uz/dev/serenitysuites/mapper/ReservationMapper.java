package uz.dev.serenitysuites.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.serenitysuites.dto.ReservationDTO;
import uz.dev.serenitysuites.entity.Reservation;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 14:17
 **/

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReservationMapper {

    @Mapping(target = "guestId", source = "guest.id")
    @Mapping(target = "roomTypeId", source = "roomType.id")
    @Mapping(target = "roomId", source = "room.id")
    ReservationDTO toDTO(Reservation reservation);

    List<ReservationDTO> toDTO(List<Reservation> reservations);

}
