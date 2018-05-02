package response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RegisterUserResponse
{
    private boolean isSuccessful;
    private String userId;

    public RegisterUserResponse(String userId)
    {
        this.isSuccessful = true;
        this.userId = userId;
    }
}
