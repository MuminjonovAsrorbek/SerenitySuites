package uz.dev.serenitysuites.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.serenitysuites.dto.RoomDTO;
import uz.dev.serenitysuites.entity.Room;
import uz.dev.serenitysuites.enums.ReservationStatus;
import uz.dev.serenitysuites.enums.RoomStatus;
import uz.dev.serenitysuites.mapper.RoomMapper;
import uz.dev.serenitysuites.repository.ReservationRepository;
import uz.dev.serenitysuites.repository.RoomRepository;
import uz.dev.serenitysuites.service.template.RoomService;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 13:30
 **/

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    private final ReservationRepository reservationRepository;

    private final RoomMapper roomMapper;

    @Override
    public List<RoomDTO> getAvailabilityRooms(LocalDate startDate, LocalDate endDate, Long roomTypeId) {

        if (startDate.isAfter(endDate)) {

            throw new IllegalArgumentException("StartDate must be before the date of departure");

        }

        List<Long> reservedRoomIds = reservationRepository
                .findReservedRoomIdsBetween(startDate, endDate,
                        List.of(ReservationStatus.CONFIRMED, ReservationStatus.CHECKED_IN));

        List<Room> rooms;

        if (roomTypeId != null) {

            if (reservedRoomIds.isEmpty()) {

                rooms = roomRepository.findByStatusAndRoomTypeId(
                        RoomStatus.AVAILABLE, roomTypeId);

            } else {

                rooms = roomRepository.findByStatusAndRoomTypeIdAndIdNotIn(
                        RoomStatus.AVAILABLE, roomTypeId, reservedRoomIds);
            }

        } else {

            if (reservedRoomIds.isEmpty()) {

                rooms = roomRepository.findByStatus(
                        RoomStatus.AVAILABLE);

            } else {

                rooms = roomRepository.findByStatusAndIdNotIn(
                        RoomStatus.AVAILABLE, reservedRoomIds);

            }

        }

        return roomMapper.toDTO(rooms);
    }
}
