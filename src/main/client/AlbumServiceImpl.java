package client;

import manager.AlbumManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import request.album.*;
import response.GenericResponse;
import response.album.*;
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
    public GenericResponse rateAlbum(RateAlbumRequest rateAlbumRequest)
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

    @Override
    public GetAlbumByIdResponse getAlbumById(GetAlbumByIdRequest getAlbumCoverRequest)
    {
        return albumManager.getAlbumById(getAlbumCoverRequest);
    }

    @Override
    public GetAlbumsOrderedByCriteriaResponse getAlbumsGrouppedByCriteria(GetAlbumsOrderedByCriteriaRequest request)
    {
        return albumManager.getAlbumsGroupedByCriteria(request);
    }
}
