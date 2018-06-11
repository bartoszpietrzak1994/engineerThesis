package service;

import org.springframework.validation.Errors;
import request.album.*;
import response.GenericResponse;
import response.album.*;

import javax.validation.Valid;

public interface AlbumService
{
    AddAlbumResponse addAlbum(@Valid AddAlbumRequest addAlbumRequest, Errors errors);
    GenericResponse rateAlbum(@Valid RateAlbumRequest addAlbumRequest, Errors errors);
    FindAllUserAlbumsResponse findAllAlbumsAddedByUser(@Valid FindAllUserAlbumRequest findAllUserAlbumRequest, Errors errors);
    GetAlbumCoverResponse getAlbumCover(@Valid GetAlbumCoverRequest getAlbumCoverRequest, Errors errors);
    GetAlbumByIdResponse getAlbumById(@Valid GetAlbumByIdRequest getAlbumCoverRequest, Errors errors);
    GetAlbumsOrderedByCriteriaResponse getAlbumsOrderedByCriteria(@Valid GetAlbumsOrderedByCriteriaRequest request, Errors errors);
}
