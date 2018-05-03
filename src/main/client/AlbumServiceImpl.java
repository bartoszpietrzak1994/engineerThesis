package client;

import manager.AlbumManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import request.album.AddAlbumRequest;
import request.album.RateAlbumRequest;
import response.album.AddAlbumResponse;
import response.album.RateAlbumResponse;
import service.AlbumService;

@Component
public class AlbumServiceImpl implements AlbumService
{
    @Autowired
    private AlbumManager albumManager;

    @Override
    public AddAlbumResponse addAlbum(AddAlbumRequest addAlbumRequest)
    {
        return albumManager.addAlbum(addAlbumRequest);
    }

    @Override
    public RateAlbumResponse addAlbum(RateAlbumRequest rateAlbumRequest)
    {
        return albumManager.rateAlbum(rateAlbumRequest);
    }
}
