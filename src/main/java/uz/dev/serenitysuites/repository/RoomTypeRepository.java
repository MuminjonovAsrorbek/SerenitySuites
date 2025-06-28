package uz.dev.serenitysuites.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import uz.dev.serenitysuites.entity.RoomType;
import uz.dev.serenitysuites.exceptions.EntityNotFoundException;

public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {

    default RoomType findByIdOrThrow(Long id) {

        return findById(id).orElseThrow(() -> new EntityNotFoundException("RoomType with id " + id + " not found", HttpStatus.NOT_FOUND));

    }

}