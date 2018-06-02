package request.user;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequest
{
    @NotNull
    @NotBlank
    private String userName;

    @NotNull
    @NotBlank
    private String password;
}
