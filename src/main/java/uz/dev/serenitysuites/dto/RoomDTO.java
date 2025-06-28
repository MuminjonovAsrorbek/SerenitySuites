package uz.dev.serenitysuites.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.dev.serenitysuites.enums.RoomStatus;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO for {@link uz.dev.serenitysuites.entity.Room}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RoomDTO implements Serializable {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp createdAt;

    @NotBlank
    private String roomNumber;

    @NotNull
    private Long roomTypeId;

    private RoomStatus status;

    @JsonIgnore
    private Long version;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<ReservationDTO> reservations;
}