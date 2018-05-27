package functional.album;

import functional.BaseFunctionalTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import service.AlbumService;
import service.AuthenticationService;

public class AlbumServiceFindAllUserAlbumsTest extends BaseFunctionalTest
{
    @Autowired
    private AlbumService albumService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private Environment environment;
}
