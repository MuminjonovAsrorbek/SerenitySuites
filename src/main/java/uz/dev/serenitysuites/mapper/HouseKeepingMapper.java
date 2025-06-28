package uz.dev.serenitysuites.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import uz.dev.serenitysuites.dto.HousekeepingLogDTO;
import uz.dev.serenitysuites.entity.HousekeepingLog;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 15:18
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface HouseKeepingMapper {

    @Mapping(target = "roomId", source = "room.id")
    @Mapping(target = "staffId", source = "staff.id")
    HousekeepingLogDTO toDTO(HousekeepingLog housekeepingLog);

    List<HousekeepingLogDTO> toDTO(List<HousekeepingLog> housekeepingLogs);

}
