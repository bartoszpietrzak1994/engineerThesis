package service;

import org.springframework.validation.Errors;
import request.album.*;
import response.GenericResponse;
import response.album.*;

import javax.validation.Valid;

public interface AlbumService
{
    AddAlbumResponse addAlbum(AddAlbumRequest addAlbumRequest);
    GenericResponse rateAlbum(RateAlbumRequest addAlbumRequest);
    FindAllUserAlbumsResponse findAllAlbumsAddedByUser(FindAllUserAlbumRequest findAllUserAlbumRequest);
    GetAlbumCoverResponse getAlbumCover(GetAlbumCoverRequest getAlbumCoverRequest);
    GetAlbumByIdResponse getAlbumById(GetAlbumByIdRequest getAlbumCoverRequest);
    GetAlbumsOrderedByCriteriaResponse getAlbumsOrderedByCriteria(GetAlbumsOrderedByCriteriaRequest request);
}
