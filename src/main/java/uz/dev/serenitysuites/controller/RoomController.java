package uz.dev.serenitysuites.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.dev.serenitysuites.dto.RoomDTO;
import uz.dev.serenitysuites.service.template.RoomService;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 13:30
 **/

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/availability")
    @PreAuthorize("hasAnyRole('GUEST' , 'ADMIN')")
    public List<RoomDTO> getAvailabilityRooms(@RequestParam LocalDate startDate,
                                              @RequestParam LocalDate endDate,
                                              @RequestParam(required = false) Long roomTypeId) {

        return roomService.getAvailabilityRooms(startDate, endDate, roomTypeId);

    }

}
