package uz.dev.serenitysuites.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import uz.dev.serenitysuites.dto.HousekeepingLogDTO;
import uz.dev.serenitysuites.dto.RoomDTO;
import uz.dev.serenitysuites.dto.response.HousekeepingTaskDTO;
import uz.dev.serenitysuites.entity.HousekeepingLog;
import uz.dev.serenitysuites.entity.Room;
import uz.dev.serenitysuites.entity.User;
import uz.dev.serenitysuites.enums.Role;
import uz.dev.serenitysuites.enums.RoomStatus;
import uz.dev.serenitysuites.enums.TaskStatus;
import uz.dev.serenitysuites.exceptions.BusinessException;
import uz.dev.serenitysuites.mapper.HouseKeepingMapper;
import uz.dev.serenitysuites.mapper.RoomMapper;
import uz.dev.serenitysuites.repository.HousekeepingLogRepository;
import uz.dev.serenitysuites.repository.RoomRepository;
import uz.dev.serenitysuites.service.template.HousekeepingService;
import uz.dev.serenitysuites.utils.SecurityUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 15:01
 **/

@Service
@RequiredArgsConstructor
public class HousekeepingServiceImpl implements HousekeepingService {

    private final RoomRepository roomRepository;

    private final HousekeepingLogRepository housekeepingLogRepository;

    private final SecurityUtils securityUtils;

    private final HouseKeepingMapper houseKeepingMapper;

    private final RoomMapper roomMapper;

    @Override
    public List<HousekeepingTaskDTO> getCleaningTasks() {

        List<Room> rooms = roomRepository.findByStatus(RoomStatus.NEEDS_CLEANING);

        return rooms.stream()
                .map(room -> HousekeepingTaskDTO.builder()
                        .roomId(room.getId())
                        .roomNumber(room.getRoomNumber())
                        .roomTypeName(room.getRoomType().getName())
                        .build()
                ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public HousekeepingLogDTO takeCleaningTask(Long roomId) {

        Room room = roomRepository.findByIdOrThrow(roomId);

        if (room.getStatus() != RoomStatus.NEEDS_CLEANING) {

            throw new BusinessException("This room is not in the cleaning list now", HttpStatus.BAD_REQUEST);

        }

        User currentUser = securityUtils.getCurrentUser();

        if (currentUser.getRole() != Role.HOUSEKEEPING) {

            throw new AccessDeniedException("Can only obtain a HouseKeeping employee");

        }

        HousekeepingLog housekeepingLog = new HousekeepingLog();

        housekeepingLog.setRoom(room);
        housekeepingLog.setStaff(currentUser);
        housekeepingLog.setStatus(TaskStatus.IN_PROGRESS);
        housekeepingLog.setStartedAt(LocalDateTime.now());

        housekeepingLogRepository.save(housekeepingLog);

        return houseKeepingMapper.toDTO(housekeepingLog);

    }

    @Override
    @Transactional
    public RoomDTO updateRoomStatus(Long roomId) {

        Room room = roomRepository.findByIdOrThrow(roomId);

        HousekeepingLog log = housekeepingLogRepository.
                findTopByRoomIdAndStatusOrderByStartedAtDescOrThrow(roomId, TaskStatus.IN_PROGRESS);

        log.setStatus(TaskStatus.COMPLETED);
        log.setCompletedAt(LocalDateTime.now());
        housekeepingLogRepository.save(log);

        room.setStatus(RoomStatus.AVAILABLE);
        roomRepository.save(room);

        return roomMapper.toDTO(room);
    }
}