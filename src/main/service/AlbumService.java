package service;

import request.album.*;
import response.GenericResponse;
import response.album.*;

public interface AlbumService
{
    GenericResponse addAlbum(AddAlbumRequest addAlbumRequest);
    GenericResponse rateAlbum(RateAlbumRequest addAlbumRequest);
    FindAllUserAlbumsResponse findAllAlbumsAddedByUser(FindAllUserAlbumRequest findAllUserAlbumRequest);
    GetAlbumCoverResponse getAlbumCover(GetAlbumCoverRequest getAlbumCoverRequest);
    GetAlbumByIdResponse getAlbumById(GetAlbumByIdRequest getAlbumCoverRequest);
    GetAlbumsOrderedByCriteriaResponse getAlbumsGrouppedByCriteria(GetAlbumsOrderedByCriteriaRequest request);
}
