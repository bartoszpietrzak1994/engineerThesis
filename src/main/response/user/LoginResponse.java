package response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse
{
    private boolean isSuccessful;
    private String errorMessage;

    public LoginResponse()
    {
        this.isSuccessful = true;
    }

    public LoginResponse(String errorMessage)
    {
        this.isSuccessful = false;
        this.errorMessage = errorMessage;
    }
}
