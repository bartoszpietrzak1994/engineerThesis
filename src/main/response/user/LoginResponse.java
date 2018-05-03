package response.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class LoginResponse
{
    private boolean isSuccessful;
    private String errorMessage;
}
