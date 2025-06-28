package uz.dev.serenitysuites.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dev.serenitysuites.dto.ReservationDTO;
import uz.dev.serenitysuites.dto.request.ReservationRequestDTO;
import uz.dev.serenitysuites.dto.response.ReservationArrivalDTO;
import uz.dev.serenitysuites.entity.Reservation;
import uz.dev.serenitysuites.entity.Room;
import uz.dev.serenitysuites.entity.RoomType;
import uz.dev.serenitysuites.entity.User;
import uz.dev.serenitysuites.enums.ReservationStatus;
import uz.dev.serenitysuites.enums.RoomStatus;
import uz.dev.serenitysuites.exceptions.BusinessException;
import uz.dev.serenitysuites.mapper.ReservationMapper;
import uz.dev.serenitysuites.repository.*;
import uz.dev.serenitysuites.service.template.ReservationService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 14:02
 **/

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    private final RoomTypeRepository roomTypeRepository;

    private final UserRepository userRepository;

    private final RoomRepository roomRepository;

    private final ReservationMapper reservationMapper;

    private final ServiceOrderRepository serviceOrderRepository;

    @Override
    @Transactional
    public ReservationDTO createReservation(ReservationRequestDTO req) {

        if (req.getCheckInDate().isAfter(req.getCheckOutDate())) {

            throw new IllegalArgumentException("The Check-in must be before the Check-out Check");

        }

        RoomType roomType = roomTypeRepository.findByIdOrThrow(req.getRoomTypeId());

        User guest = userRepository.findByIdOrThrow(req.getGuestId());

        List<Long> repositoryReservedRoomIdsBetween = reservationRepository.
                findReservedRoomIdsBetween(
                        req.getCheckInDate(),
                        req.getCheckOutDate(),
                        List.of(ReservationStatus.CONFIRMED, ReservationStatus.CHECKED_IN)
                );

        long availableCount;

        if (repositoryReservedRoomIdsBetween.isEmpty()) {

            availableCount = roomRepository.countByRoomTypeIdAndStatus(
                    roomType.getId(),
                    RoomStatus.AVAILABLE
            );

        } else {

            availableCount = roomRepository.countByRoomTypeIdAndStatusAndIdNotIn(
                    roomType.getId(),
                    RoomStatus.AVAILABLE,
                    repositoryReservedRoomIdsBetween
            );

        }

        if (availableCount == 0) {

            throw new BusinessException("This type of room is not available on selected dates", HttpStatus.NOT_FOUND);

        }

        long days = ChronoUnit.DAYS.between(req.getCheckInDate(), req.getCheckOutDate());

        long calculatedPrice = roomType.getBasePrice() * days;

        Reservation reservation = new Reservation();

        reservation.setGuest(guest);
        reservation.setRoomType(roomType);
        reservation.setCheckInDate(req.getCheckInDate());
        reservation.setCheckOutDate(req.getCheckOutDate());
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setTotalPrice(calculatedPrice);

        reservationRepository.save(reservation);

        return reservationMapper.toDTO(reservation);
    }

    @Override
    public List<ReservationArrivalDTO> getTodayArrivals() {

        LocalDate today = LocalDate.now();

        List<Reservation> reservations = reservationRepository.findByCheckInDateAndStatus(today, ReservationStatus.CONFIRMED);

        return reservations.stream()
                .map(res -> ReservationArrivalDTO.builder()
                        .reservationId(res.getId())
                        .guestId(res.getGuest().getId())
                        .guestFullName(res.getGuest().getFullName())
                        .roomTypeName(res.getRoomType().getName())
                        .checkInDate(res.getCheckInDate())
                        .checkOutDate(res.getCheckOutDate())
                        .totalPrice(res.getTotalPrice())
                        .build()
                ).collect(Collectors.toList());

    }

    @Override
    @Transactional
    public ReservationDTO checkInReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findByIdOrThrow(reservationId);

        if (reservation.getStatus() != ReservationStatus.CONFIRMED) {

            throw new BusinessException("Only CONFIRMED STATUS CURRENCY can be checked", HttpStatus.BAD_REQUEST);

        }

        Long roomTypeId = reservation.getRoomType().getId();

        Room room = roomRepository.findFirstByRoomTypeIdAndStatusOrThrow(roomTypeId, RoomStatus.AVAILABLE);

        reservation.setRoom(room);
        reservation.setStatus(ReservationStatus.CHECKED_IN);

        room.setStatus(RoomStatus.OCCUPIED);

        reservationRepository.save(reservation);
        roomRepository.save(room);

        return reservationMapper.toDTO(reservation);
    }

    @Override
    @Transactional
    public ReservationDTO checkOutReservation(Long reservationId) {

        Reservation reservation = reservationRepository.findByIdOrThrow(reservationId);

        if (reservation.getStatus() != ReservationStatus.CHECKED_IN) {

            throw new BusinessException("Only checked_in Status can you make a Check-out", HttpStatus.BAD_REQUEST);

        }

        Long servicesTotal = serviceOrderRepository.sumPriceByReservationId(reservationId);

        Long finalTotal = reservation.getTotalPrice() + servicesTotal;
        reservation.setTotalPrice(finalTotal);

        reservation.setStatus(ReservationStatus.COMPLETED);


        Room room = reservation.getRoom();

        if (room != null) {

            room.setStatus(RoomStatus.NEEDS_CLEANING);

            roomRepository.save(room);
        }

        reservationRepository.save(reservation);

        return reservationMapper.toDTO(reservation);

    }

}
