package uz.dev.serenitysuites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.serenitysuites.entity.HousekeepingLog;
import uz.dev.serenitysuites.enums.TaskStatus;
import uz.dev.serenitysuites.exceptions.BusinessException;

import java.util.Optional;

public interface HousekeepingLogRepository extends JpaRepository<HousekeepingLog, Long> {

    Optional<HousekeepingLog> findTopByRoomIdAndStatusOrderByStartedAtDesc(Long roomId, TaskStatus taskStatus);

    default HousekeepingLog findTopByRoomIdAndStatusOrderByStartedAtDescOrThrow(Long roomId, TaskStatus taskStatus) {

        return this.findTopByRoomIdAndStatusOrderByStartedAtDesc(roomId, taskStatus).
                orElseThrow(() -> new BusinessException("The cleaning task was not found or not in-progress", HttpStatus.NOT_FOUND));

    }

}