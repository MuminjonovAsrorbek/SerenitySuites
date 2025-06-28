package uz.dev.serenitysuites.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.serenitysuites.dto.HousekeepingLogDTO;
import uz.dev.serenitysuites.dto.RoomDTO;
import uz.dev.serenitysuites.dto.response.HousekeepingTaskDTO;
import uz.dev.serenitysuites.service.template.HousekeepingService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 14:59
 **/

@RestController
@RequestMapping("/api/v1/housekeeping")
@RequiredArgsConstructor
public class HouseKeepingController {

    private final HousekeepingService housekeepingService;

    @GetMapping("/tasks")
    @PreAuthorize("hasAnyRole('HOUSEKEEPING' , 'ADMIN')")
    public List<HousekeepingTaskDTO> getCleaningTasks() {

        return housekeepingService.getCleaningTasks();

    }

    @PostMapping("/{roomId}/take")
    @PreAuthorize("hasAnyRole('HOUSEKEEPING' , 'ADMIN')")
    public HousekeepingLogDTO takeTask(@PathVariable Long roomId) {

        return housekeepingService.takeCleaningTask(roomId);

    }


    @PatchMapping("/{roomId}/status")
    @PreAuthorize("hasAnyRole('HOUSEKEEPING','ADMIN')")
    public RoomDTO updateStatus(@PathVariable Long roomId) {

        return housekeepingService.updateRoomStatus(roomId);

    }

}
