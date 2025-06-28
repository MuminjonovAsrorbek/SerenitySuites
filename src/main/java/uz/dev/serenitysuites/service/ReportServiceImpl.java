package uz.dev.serenitysuites.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.dev.serenitysuites.dto.response.OccupancyReportDTO;
import uz.dev.serenitysuites.repository.ReservationRepository;
import uz.dev.serenitysuites.repository.RoomRepository;
import uz.dev.serenitysuites.service.template.ReportService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 15:39
 **/

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReservationRepository reservationRepo;

    private final RoomRepository roomRepo;

    @Override
    public OccupancyReportDTO getOccupancyReport(LocalDate startDate, LocalDate endDate) {

        if (startDate.isAfter(endDate)) {

            throw new IllegalArgumentException("StartDate must be before the date of departure");

        }

        long totalRooms = roomRepo.count();
        long daysInPeriod = ChronoUnit.DAYS.between(startDate, endDate);

        daysInPeriod = daysInPeriod > 0 ? daysInPeriod : 0;

        long totalRoomNights = totalRooms * daysInPeriod;

        long occupiedNights = reservationRepo
                .sumBookedNights(startDate, endDate);

        double rate = totalRoomNights == 0
                ? 0.0
                : (occupiedNights / (double) totalRoomNights) * 100.0;

        return OccupancyReportDTO.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalRooms(totalRooms)
                .totalRoomNights(totalRoomNights)
                .occupiedRoomNights(occupiedNights)
                .occupancyRatePercent(Math.round(rate * 100.0) / 100.0)
                .build();

    }
}
