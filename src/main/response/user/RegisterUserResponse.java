package response.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserResponse
{
    private boolean isSuccessful;
    private String userId;

    public RegisterUserResponse()
    {
        this.isSuccessful = false;
        this.userId = null;
    }

    public RegisterUserResponse(String userId)
    {
        this.isSuccessful = true;
        this.userId = userId;
    }
}
