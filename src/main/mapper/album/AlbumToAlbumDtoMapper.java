package mapper.album;

import dto.album.AlbumDto;
import model.album.Album;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
final public class AlbumToAlbumDtoMapper
{
    public static AlbumDto map(Album album)
    {
        return AlbumDto.builder()
                .albumId(album.getAlbumId().toString())
                .albumRating(album.getAlbumRating() != null ? album.getAlbumRating().toString() : null)
                .albumCover(album.getAlbumCover())
                .artist(album.getArtist())
                .releaseDate(new SimpleDateFormat("dd/mm/yyy").format(album.getReleaseDate()))
                .title(album.getTitle())
                .build();
    }
}
