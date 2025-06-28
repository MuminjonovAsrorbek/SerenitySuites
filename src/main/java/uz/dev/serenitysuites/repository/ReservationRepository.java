package uz.dev.serenitysuites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import uz.dev.serenitysuites.entity.Reservation;
import uz.dev.serenitysuites.enums.ReservationStatus;
import uz.dev.serenitysuites.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
                SELECT DISTINCT r.room.id
                FROM Reservation r
                WHERE r.status IN :statuses
                  AND r.checkInDate <= :endDate
                  AND r.checkOutDate >= :startDate
            """)
    List<Long> findReservedRoomIdsBetween(@Param("startDate") LocalDate startDate,
                                          @Param("endDate") LocalDate endDate,
                                          @Param("statuses") List<ReservationStatus> statuses);

    List<Reservation> findByCheckInDateAndStatus(
            LocalDate checkInDate,
            ReservationStatus status
    );

    default Reservation findByIdOrThrow(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found with ID : " + id, HttpStatus.NOT_FOUND));

    }

    @Query(value = """
        SELECT COALESCE(
          SUM(
            (LEAST(check_out_date, :endDate)::date
             - GREATEST(check_in_date, :startDate)::date)
          ),
          0
        )
        FROM reservation
        WHERE status IN ('CONFIRMED','CHECKED_IN','COMPLETED')
          AND check_in_date <= :endDate
          AND check_out_date >= :startDate
    """, nativeQuery = true)
    long sumBookedNights(
            @Param("startDate") LocalDate startDate,
            @Param("endDate")   LocalDate endDate
    );
}