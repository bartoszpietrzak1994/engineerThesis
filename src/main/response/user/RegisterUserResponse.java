package response.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import response.GenericResponse;

@NoArgsConstructor
public class RegisterUserResponse extends GenericResponse
{
    @Getter
    private String userId;

    public RegisterUserResponse(String userId)
    {
        this.isSuccessful = true;
        this.userId = userId;
    }
}
