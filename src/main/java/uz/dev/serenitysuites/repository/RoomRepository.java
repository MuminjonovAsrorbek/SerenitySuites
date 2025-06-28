package uz.dev.serenitysuites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.serenitysuites.entity.Room;
import uz.dev.serenitysuites.enums.RoomStatus;
import uz.dev.serenitysuites.exceptions.BusinessException;
import uz.dev.serenitysuites.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByStatusAndRoomTypeIdAndIdNotIn(RoomStatus roomStatus, Long roomTypeId, List<Long> reservedRoomIds);

    List<Room> findByStatusAndIdNotIn(RoomStatus roomStatus, List<Long> reservedRoomIds);

    long countByRoomTypeIdAndStatusAndIdNotIn(Long id, RoomStatus roomStatus, List<Long> repositoryReservedRoomIdsBetween);

    Optional<Room> findFirstByRoomTypeIdAndStatus(Long roomTypeId, RoomStatus status);

    default Room findFirstByRoomTypeIdAndStatusOrThrow(Long roomTypeId, RoomStatus status) {

        return this.findFirstByRoomTypeIdAndStatus(roomTypeId, status)
                .orElseThrow(() -> new BusinessException("Tur ID=%d bo‘yicha bo‘sh xona topilmadi".formatted(roomTypeId), HttpStatus.NOT_FOUND));

    }

    List<Room> findByStatus(RoomStatus roomStatus);

    default Room findByIdOrThrow(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("Room not found with ID : " + id, HttpStatus.NOT_FOUND));

    }

    List<Room> findByStatusAndRoomTypeId(RoomStatus roomStatus, Long roomTypeId);

    long countByRoomTypeIdAndStatus(Long id, RoomStatus roomStatus);
}