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
import response.GenericResponse;
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
public class AlbumManager
{
    private AlbumRepository albumRepository;
    private AuthenticationService authenticationService;
    private AlbumSortingMethodResolver albumSortingMethodResolver;
    private RequestValidator requestValidator;

    @Autowired
    public AlbumManager(AlbumRepository albumRepository, AuthenticationService authenticationService,
                        AlbumSortingMethodResolver albumSortingMethodResolver, RequestValidator requestValidator,
                        Environment environment)
    {
        this.albumRepository = albumRepository;
        this.authenticationService = authenticationService;
        this.albumSortingMethodResolver = albumSortingMethodResolver;
        this.requestValidator = requestValidator;
        this.environment = environment;
    }

    @Autowired
    private Environment environment;

    public AddAlbumResponse addAlbum(AddAlbumRequest addAlbumRequest)
    {
        Set<ConstraintViolation<AddAlbumRequest>> validationErrors = this.requestValidator.validate(addAlbumRequest);

        if (!validationErrors.isEmpty())
        {
            return this.prepareValidationResponse(new AddAlbumResponse(), Iterables.getFirst(validationErrors, null)
                    .getMessage());
        }

        AddAlbumResponse addAlbumResponse = new AddAlbumResponse();
        User user = authenticationService.findUserByUsername(addAlbumRequest.getUserName());

        if (user == null)
        {
            addAlbumResponse.setSuccessful(false);
            addAlbumResponse.setErrorMessage(this.environment.getProperty("user.not_found"));

            return addAlbumResponse;
        }

        Album album = new Album();

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

        Album savedAlbum;
        try
        {
            savedAlbum = albumRepository.save(album);
        }
        catch (Exception e)
        {
            addAlbumResponse.setSuccessful(false);
            addAlbumResponse.setErrorMessage(this.environment.getProperty("album.add_album_error"));

            return addAlbumResponse;
        }

        return new AddAlbumResponse(String.valueOf(savedAlbum.getAlbumId()));
    }

    public GenericResponse rateAlbum(RateAlbumRequest rateAlbumRequest)
    {
        Set<ConstraintViolation<RateAlbumRequest>> validationErrors = this.requestValidator.validate(rateAlbumRequest);

        if (!validationErrors.isEmpty())
        {
            return this.prepareValidationResponse(new GenericResponse(), Iterables.getFirst(validationErrors, null)
                    .getMessage());
        }

        Long albumId;

        try
        {
            albumId = Long.valueOf(rateAlbumRequest.getAlbumId());
        }
        catch (NumberFormatException e)
        {
            GenericResponse rateAlbumResponse = new GenericResponse();
            rateAlbumResponse.setSuccessful(false);
            rateAlbumResponse.setErrorMessage(this.environment.getProperty("album.unexpected_error"));

            return rateAlbumResponse;
        }

        Album album = albumRepository.getOne(albumId);

        if (album == null)
        {
            GenericResponse rateAlbumResponse = new GenericResponse();
            rateAlbumResponse.setSuccessful(false);
            rateAlbumResponse.setErrorMessage(this.environment.getProperty("album.not_found"));

            return rateAlbumResponse;
        }

        album.setAlbumRating(AlbumRating.valueOf(rateAlbumRequest.getAlbumRating()));
        album.setRatingDate(getCurrentDate());

        try
        {
            albumRepository.save(album);
        }
        catch (Exception e)
        {
            GenericResponse rateAlbumResponse = new GenericResponse();
            rateAlbumResponse.setSuccessful(false);
            rateAlbumResponse.setErrorMessage(this.environment.getProperty("album.unexpected_error"));

            return rateAlbumResponse;
        }

        return new GenericResponse(true);
    }

    public FindAllUserAlbumsResponse findAllUserAlbums(FindAllUserAlbumRequest findAllUserAlbumRequest)
    {
        Set<ConstraintViolation<FindAllUserAlbumRequest>> validationErrors = this.requestValidator.validate
                (findAllUserAlbumRequest);

        if (!validationErrors.isEmpty())
        {
            return this.prepareValidationResponse(new FindAllUserAlbumsResponse(), Iterables.getFirst
                    (validationErrors, null).getMessage());
        }

        User user = authenticationService.findUserByUsername(findAllUserAlbumRequest.getUserName());

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

        return new FindAllUserAlbumsResponse(userAlbums.stream().map(AlbumToAlbumDtoMapper::map).collect(Collectors
                .toList()));
    }

    public GetAlbumCoverResponse getAlbumCover(GetAlbumCoverRequest getAlbumCoverRequest)
    {
        Set<ConstraintViolation<GetAlbumCoverRequest>> validationErrors = this.requestValidator.validate
                (getAlbumCoverRequest);

        if (!validationErrors.isEmpty())
        {
            return this.prepareValidationResponse(new GetAlbumCoverResponse(), Iterables.getFirst(validationErrors,
                    null).getMessage());
        }

        Long albumId;

        try
        {
            albumId = Long.valueOf(getAlbumCoverRequest.getAlbumId());
        }
        catch (NumberFormatException e)
        {
            GetAlbumCoverResponse getAlbumCoverResponse = new GetAlbumCoverResponse();
            getAlbumCoverResponse.setSuccessful(false);
            getAlbumCoverResponse.setErrorMessage(this.environment.getProperty("album.unexpected_error"));

            return getAlbumCoverResponse;
        }

        Optional<Album> albumOptional = albumRepository.findById(albumId);

        GetAlbumCoverResponse response = new GetAlbumCoverResponse();

        if (!albumOptional.isPresent())
        {
            response.setSuccessful(false);
            response.setErrorMessage(this.environment.getProperty("album.not_found"));
            return response;
        }

        Album album = albumOptional.get();

        byte[] albumCover = album.getAlbumCover();
        if (albumCover == null)
        {
            response.setSuccessful(false);
            response.setErrorMessage(this.environment.getProperty("album.cover_not_found"));
            return response;
        }

        return new GetAlbumCoverResponse(albumCover);
    }

    public GetAlbumByIdResponse getAlbumById(GetAlbumByIdRequest getAlbumByIdRequest)
    {
        Long albumId;

        try
        {
            albumId = Long.valueOf(getAlbumByIdRequest.getAlbumId());
        }
        catch (NumberFormatException e)
        {
            GetAlbumByIdResponse getAlbumByIdResponse = new GetAlbumByIdResponse();
            getAlbumByIdResponse.setSuccessful(false);
            getAlbumByIdResponse.setErrorMessage(this.environment.getProperty("album.unexpected_error"));

            return getAlbumByIdResponse;
        }

        Optional<Album> albumById = albumRepository.findById(albumId);

        if (!albumById.isPresent())
        {
            GetAlbumByIdResponse response = new GetAlbumByIdResponse();
            response.setSuccessful(false);
            response.setErrorMessage(this.environment.getProperty("album.not_found"));
            return response;
        }

        return new GetAlbumByIdResponse(AlbumToAlbumDtoMapper.map(albumById.get()));
    }

    public GetAlbumsOrderedByCriteriaResponse getAlbumsGroupedByCriteria(GetAlbumsOrderedByCriteriaRequest request)
    {
        AlbumOrderingCriteria albumSortingCriteria = AlbumOrderingCriteria.valueOf(request.getSortingCriteria());

        AlbumSortingMethod sortingMethod = albumSortingMethodResolver.resolve(albumSortingCriteria);

        List<Album> sortedAlbums = null;
        try
        {
            sortedAlbums = sortingMethod.sort();
        }
        catch (Exception e)
        {
            GetAlbumsOrderedByCriteriaResponse response = new GetAlbumsOrderedByCriteriaResponse();
            response.setSuccessful(false);
            response.setErrorMessage(e.getMessage());
        }

        return new GetAlbumsOrderedByCriteriaResponse(sortedAlbums.stream().map(AlbumToAlbumDtoMapper::map).collect
                (Collectors.toList()));
    }

    private Date getCurrentDate()
    {
        return new Date(new java.util.Date().getTime());
    }

    private <T extends GenericResponse> T prepareValidationResponse(T response, String errorMessage)
    {
        response.setSuccessful(false);
        response.setErrorMessage(errorMessage);
        return response;
    }
}
