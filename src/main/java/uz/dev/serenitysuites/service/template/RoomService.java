package uz.dev.serenitysuites.service.template;

import uz.dev.serenitysuites.dto.RoomDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 13:30
 **/

public interface RoomService {

    List<RoomDTO> getAvailabilityRooms(LocalDate startDate, LocalDate endDate, Long roomTypeId);

}
