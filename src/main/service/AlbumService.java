package service;

import request.album.*;
import response.album.*;

public interface AlbumService
{
    AddAlbumResponse addAlbum(AddAlbumRequest addAlbumRequest);
    RateAlbumResponse rateAlbum(RateAlbumRequest addAlbumRequest);
    FindAllUserAlbumsResponse findAllAlbumsAddedByUser(FindAllUserAlbumRequest findAllUserAlbumRequest);
    GetAlbumCoverResponse getAlbumCover(GetAlbumCoverRequest getAlbumCoverRequest);
    GetAlbumByIdResponse getAlbumById(GetAlbumByIdRequest getAlbumCoverRequest);
    GetAlbumsOrderedByCriteriaResponse getAlbumsGrouppedByCriteria(GetAlbumsOrderedByCriteriaRequest request);
}
