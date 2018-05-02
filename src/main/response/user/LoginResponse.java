package response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginResponse
{
    private boolean isSuccessful;
    private String errorMessage;

    public LoginResponse(String errorMessage)
    {
        this.isSuccessful = false;
        this.errorMessage = errorMessage;
    }
}
