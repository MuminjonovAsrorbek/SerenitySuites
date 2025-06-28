package uz.dev.serenitysuites.service.template;

import uz.dev.serenitysuites.dto.ReservationDTO;
import uz.dev.serenitysuites.dto.request.ReservationRequestDTO;
import uz.dev.serenitysuites.dto.response.ReservationArrivalDTO;

import java.util.List;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 14:02
 **/

public interface ReservationService {

    ReservationDTO createReservation(ReservationRequestDTO dto);

    List<ReservationArrivalDTO> getTodayArrivals();

    ReservationDTO checkInReservation(Long reservationId);

    ReservationDTO checkOutReservation(Long reservationId);

}
