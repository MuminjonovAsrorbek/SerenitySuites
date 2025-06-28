package uz.dev.serenitysuites.service.template;

import uz.dev.serenitysuites.dto.HousekeepingLogDTO;
import uz.dev.serenitysuites.dto.RoomDTO;
import uz.dev.serenitysuites.dto.response.HousekeepingTaskDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 15:01
 **/

public interface HousekeepingService {

    List<HousekeepingTaskDTO> getCleaningTasks();

    HousekeepingLogDTO takeCleaningTask(Long roomId);

    RoomDTO updateRoomStatus(Long roomId);
}
