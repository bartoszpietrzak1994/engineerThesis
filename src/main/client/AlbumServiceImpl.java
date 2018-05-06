package client;

import manager.AlbumManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import request.album.AddAlbumRequest;
import request.album.FindAllUserAlbumRequest;
import request.album.GetAlbumCoverRequest;
import request.album.RateAlbumRequest;
import response.album.AddAlbumResponse;
import response.album.FindAllUserAlbumsResponse;
import response.album.GetAlbumCoverResponse;
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
    public RateAlbumResponse rateAlbum(RateAlbumRequest rateAlbumRequest)
    {
        return albumManager.rateAlbum(rateAlbumRequest);
    }

    @Override
    public FindAllUserAlbumsResponse findAllAlbumsAddedByUser(FindAllUserAlbumRequest findAllUserAlbumRequest)
    {
        return albumManager.findAllUserAlbums(findAllUserAlbumRequest);
    }

    @Override
    public GetAlbumCoverResponse getAlbumCover(GetAlbumCoverRequest getAlbumCoverRequest)
    {
        return albumManager.getAlbumCover(getAlbumCoverRequest);
    }
}
