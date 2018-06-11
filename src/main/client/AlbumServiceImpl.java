package client;

import manager.AlbumManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import request.album.*;
import response.GenericResponse;
import response.album.*;
import service.AlbumService;

import javax.validation.Valid;

@Component
public class AlbumServiceImpl implements AlbumService
{
    @Autowired
    private AlbumManager albumManager;

    @Override
    public AddAlbumResponse addAlbum(@Valid AddAlbumRequest addAlbumRequest, Errors errors)
    {
        return albumManager.addAlbum(addAlbumRequest);
    }

    @Override
    public GenericResponse rateAlbum(@Valid RateAlbumRequest rateAlbumRequest, Errors errors)
    {
        return albumManager.rateAlbum(rateAlbumRequest);
    }

    @Override
    public FindAllUserAlbumsResponse findAllAlbumsAddedByUser(@Valid FindAllUserAlbumRequest findAllUserAlbumRequest, Errors errors)
    {
        return albumManager.findAllUserAlbums(findAllUserAlbumRequest);
    }

    @Override
    public GetAlbumCoverResponse getAlbumCover(@Valid GetAlbumCoverRequest getAlbumCoverRequest, Errors errors)
    {
        return albumManager.getAlbumCover(getAlbumCoverRequest);
    }

    @Override
    public GetAlbumByIdResponse getAlbumById(@Valid GetAlbumByIdRequest getAlbumCoverRequest, Errors errors)
    {
        return albumManager.getAlbumById(getAlbumCoverRequest);
    }

    @Override
    public GetAlbumsOrderedByCriteriaResponse getAlbumsOrderedByCriteria(
            @Valid GetAlbumsOrderedByCriteriaRequest request,
            Errors errors
    ) {
        return albumManager.getAlbumsGroupedByCriteria(request);
    }
}
