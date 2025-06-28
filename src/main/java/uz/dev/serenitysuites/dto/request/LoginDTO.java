package uz.dev.serenitysuites.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by: asrorbek
 * DateTime: 6/21/25 14:08
 **/

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {

    private String email;

    private String password;

}
