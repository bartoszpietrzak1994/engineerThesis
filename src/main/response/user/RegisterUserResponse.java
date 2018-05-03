package response.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RegisterUserResponse
{
    private boolean isSuccessful;
    private String errorMessage;
    private String userId;

    public RegisterUserResponse(String userId)
    {
        this.isSuccessful = true;
        this.userId = userId;
    }
}
