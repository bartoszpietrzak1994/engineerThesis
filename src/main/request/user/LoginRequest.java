package request.user;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class LoginRequest
{
    @NotNull
    @NotBlank
    private String userName;

    @NotNull
    @NotBlank
    private String password;
}
