package uz.dev.serenitysuites.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/28/25 15:02
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class HousekeepingTaskDTO {

    private Long roomId;

    private String roomNumber;

    private String roomTypeName;
}
