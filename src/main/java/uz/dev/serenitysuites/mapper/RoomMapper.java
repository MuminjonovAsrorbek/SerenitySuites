package uz.dev.serenitysuites.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.serenitysuites.dto.RoomDTO;
import uz.dev.serenitysuites.entity.Room;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 13:47
 **/

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ReservationMapper.class)
public interface RoomMapper {

    @Mapping(target = "roomTypeId", source = "roomType.id")
    RoomDTO toDTO(Room room);

    List<RoomDTO> toDTO(List<Room> rooms);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "roomType", ignore = true)
    Room toEntity(RoomDTO roomDTO);

}
