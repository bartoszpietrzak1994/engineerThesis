package service;

import request.album.AddAlbumRequest;
import request.album.FindAllUserAlbumRequest;
import request.album.RateAlbumRequest;
import response.album.AddAlbumResponse;
import response.album.FindAllUserAlbumsResponse;
import response.album.RateAlbumResponse;

public interface AlbumService
{
    AddAlbumResponse addAlbum(AddAlbumRequest addAlbumRequest);
    RateAlbumResponse rateAlbum(RateAlbumRequest addAlbumRequest);
    FindAllUserAlbumsResponse findAllAlbumsAddedByUser(FindAllUserAlbumRequest findAllUserAlbumRequest);
}
