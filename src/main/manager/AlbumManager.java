package manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;
import request.album.AddAlbumRequest;
import request.album.RateAlbumRequest;
import response.album.AddAlbumResponse;
import response.album.RateAlbumResponse;

@Component
final public class AlbumManager
{
    @Autowired
    private AlbumRepository albumRepository;

    public AddAlbumResponse addAlbum(AddAlbumRequest addAlbumRequest)
    {
        return null;
    }

    public RateAlbumResponse rateAlbum(RateAlbumRequest rateAlbumRequest)
    {
        return null;
    }
}
