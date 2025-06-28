package uz.dev.serenitysuites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.dev.serenitysuites.entity.ServiceOrder;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {

    @Query("""
              SELECT COALESCE(SUM(so.price), 0)
              FROM ServiceOrder so
              WHERE so.reservation.id = :reservationId
            """)
    Long sumPriceByReservationId(@Param("reservationId") Long reservationId);

}