package uz.dev.serenitysuites.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.serenitysuites.dto.ReservationDTO;
import uz.dev.serenitysuites.dto.request.ReservationRequestDTO;
import uz.dev.serenitysuites.dto.response.ReservationArrivalDTO;
import uz.dev.serenitysuites.service.template.ReservationService;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 13:57
 **/

@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('GUEST' ,'ADMIN')")
    public ResponseEntity<ReservationDTO> createReservation(@Valid @RequestBody ReservationRequestDTO dto) {

        ReservationDTO reservation = reservationService.createReservation(dto);

        return ResponseEntity.ok().body(reservation);

    }

    @GetMapping("/arrivals")
    @PreAuthorize("hasAnyRole('FRONT_DESK', 'ADMIN')")
    public List<ReservationArrivalDTO> getTodayArrivals() {

        return reservationService.getTodayArrivals();

    }

    @PostMapping("/{reservationId}/check-in")
    @PreAuthorize("hasAnyRole('FRONT_DESK', 'ADMIN')")
    public ReservationDTO checkIn(@PathVariable Long reservationId) {

        return reservationService.checkInReservation(reservationId);

    }

    @PostMapping("/{reservationId}/check-out")
    @PreAuthorize("hasAnyRole('FRONT_DESK', 'ADMIN')")
    public ReservationDTO checkOut(@PathVariable Long reservationId) {

        return reservationService.checkOutReservation(reservationId);

    }

}
