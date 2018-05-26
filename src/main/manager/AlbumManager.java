package manager;

import com.google.common.collect.Iterables;
import manager.sorting.AlbumSortingMethod;
import mapper.album.AlbumToAlbumDtoMapper;
import model.album.Album;
import model.album.AlbumRating;
import model.album.AlbumOrderingCriteria;
import model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import repository.AlbumRepository;
import request.album.*;
import response.album.*;
import service.AuthenticationService;
import util.album.AlbumSortingMethodResolver;
import validation.RequestValidator;

import javax.validation.ConstraintViolation;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
final public class AlbumManager
{
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AlbumSortingMethodResolver albumSortingMethodResolver;

    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private Environment environment;

    public AddAlbumResponse addAlbum(AddAlbumRequest addAlbumRequest)
    {
        Album album = new Album();

        AddAlbumResponse addAlbumResponse = new AddAlbumResponse();

        Set<ConstraintViolation<AddAlbumRequest>> validationErrors = this.requestValidator.validate(addAlbumRequest);

        if (!validationErrors.isEmpty())
        {
            addAlbumResponse.setSuccessful(false);
            addAlbumResponse.setErrorMessage(Iterables.getFirst(validationErrors, null).getMessage());
            return addAlbumResponse;
        }

        User user = authenticationService.findUserByUsername(addAlbumRequest.getUserName());

        if (user == null)
        {
            addAlbumResponse.setSuccessful(false);
            addAlbumResponse.setErrorMessage(this.environment.getProperty("user.not_found"));

            return addAlbumResponse;
        }

        album.setUser(user);
        album.setArtist(addAlbumRequest.getArtist());
        album.setTitle(addAlbumRequest.getTitle());

        if (addAlbumRequest.getAlbumRating() != null)
        {
            album.setRatingDate(getCurrentDate());
            album.setAlbumRating(addAlbumRequest.getAlbumRating());
        }

        album.setAlbumCover(addAlbumRequest.getAlbumCover());
        album.setReleaseDate(Date.valueOf(addAlbumRequest.getReleaseDate()));

        try
        {
            albumRepository.save(album);
        }
        catch (Exception e)
        {
            addAlbumResponse.setSuccessful(false);
            addAlbumResponse.setErrorMessage(this.environment.getProperty("album.add_album_error"));

            return addAlbumResponse;
        }

        addAlbumResponse.setSuccessful(true);

        return addAlbumResponse;
    }

    public RateAlbumResponse rateAlbum(RateAlbumRequest rateAlbumRequest)
    {
        RateAlbumResponse rateAlbumResponse = new RateAlbumResponse();

        Set<ConstraintViolation<RateAlbumRequest>> validationErrors = this.requestValidator.validate(rateAlbumRequest);

        if (!validationErrors.isEmpty())
        {
            rateAlbumResponse.setSuccessful(false);
            rateAlbumResponse.setErrorMessage(Iterables.getFirst(validationErrors, null).getMessage());
            return rateAlbumResponse;
        }

        Album album = albumRepository.getOne(Long.valueOf(rateAlbumRequest.getAlbumId()));

        album.setAlbumRating(AlbumRating.valueOf(rateAlbumRequest.getAlbumRating()));
        album.setRatingDate(getCurrentDate());

        try
        {
            albumRepository.save(album);
        }
        catch (Exception e)
        {
            rateAlbumResponse.setSuccessful(false);
            rateAlbumResponse.setErrorMessage(e.getMessage());

            return rateAlbumResponse;
        }

        rateAlbumResponse.setSuccessful(true);

        return rateAlbumResponse;
    }

    public FindAllUserAlbumsResponse findAllUserAlbums(FindAllUserAlbumRequest findAllUserAlbumRequest)
    {
        String userName = findAllUserAlbumRequest.getUserName();
        User user = authenticationService.findUserByUsername(userName);

        FindAllUserAlbumsResponse response = new FindAllUserAlbumsResponse();
        List<Album> userAlbums;

        try
        {
             userAlbums = albumRepository.findAllByUser(user);
        }
        catch (Exception e)
        {
            response.setSuccessful(false);
            response.setErrorMessage(e.getMessage());
            return response;
        }

        response.setSuccessful(true);
        response.setAlbumList(userAlbums.stream().map(AlbumToAlbumDtoMapper::map).collect(Collectors.toList()));

        return response;
    }

    public GetAlbumCoverResponse getAlbumCover(GetAlbumCoverRequest getAlbumCoverRequest)
    {
        Optional<Album> albumOptional = albumRepository.findById(Long.valueOf(getAlbumCoverRequest.getAlbumId()));

        GetAlbumCoverResponse response = new GetAlbumCoverResponse();

        if (!albumOptional.isPresent())
        {
            response.setSuccessful(false);
            return response;
        }

        Album album = albumOptional.get();
        response.setSuccessful(true);

        byte[] albumCover = album.getAlbumCover();
        if (albumCover == null)
        {
            response.setSuccessful(false);
            return response;
        }

        response.setAlbumCover(albumCover);

        return response;
    }

    public GetAlbumByIdResponse getAlbumById(GetAlbumByIdRequest getAlbumByIdRequest)
    {
        Optional<Album> albumById = albumRepository.findById(Long.valueOf(getAlbumByIdRequest.getAlbumId()));

        GetAlbumByIdResponse response = new GetAlbumByIdResponse();

        if (!albumById.isPresent())
        {
            response.setSuccessful(false);
            return response;
        }

        Album album = albumById.get();
        response.setSuccessful(true);
        response.setAlbum(AlbumToAlbumDtoMapper.map(album));

        return response;
    }

    public GetAlbumsOrderedByCriteriaResponse getAlbumsGrouppedByCriteria(GetAlbumsOrderedByCriteriaRequest request)
    {
        AlbumOrderingCriteria albumSortingCriteria = AlbumOrderingCriteria.valueOf(request.getSortingCriteria());

        AlbumSortingMethod sortingMethod = albumSortingMethodResolver.resolve(albumSortingCriteria);

        GetAlbumsOrderedByCriteriaResponse response = new GetAlbumsOrderedByCriteriaResponse();

        try
        {
            List<Album> sortedAlbums = sortingMethod.sort();
            response.setSuccessful(true);
            response.setAlbums(sortedAlbums.stream().map(AlbumToAlbumDtoMapper::map).collect(Collectors.toList()));
        }
        catch (Exception e)
        {
            response.setSuccessful(false);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }

    private Date getCurrentDate()
    {
        return new Date(new java.util.Date().getTime());
    }
}
