package response;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class GenericResponse
{
    protected boolean isSuccessful;
    protected String errorMessage;

    public GenericResponse(boolean isSuccessful)
    {
        this.isSuccessful = isSuccessful;
    }
}
