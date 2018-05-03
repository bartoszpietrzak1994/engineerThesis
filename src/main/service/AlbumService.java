package service;

import request.album.AddAlbumRequest;
import request.album.RateAlbumRequest;
import response.album.AddAlbumResponse;
import response.album.RateAlbumResponse;

public interface AlbumService
{
    AddAlbumResponse addAlbum(AddAlbumRequest addAlbumRequest);
    RateAlbumResponse addAlbum(RateAlbumRequest addAlbumRequest);
}
