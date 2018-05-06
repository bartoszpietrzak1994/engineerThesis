package model.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.user.User;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="album")
@Data
@AllArgsConstructor
@NoArgsConstructor
final public class Album
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "album_id", nullable = false, unique = true, length = 5)
    private Long albumId;

    @Column(name = "artist", nullable = false, unique = true, length = 50)
    private String artist;

    @Column(name = "title", nullable = false, unique = true, length = 50)
    private String title;

    @Column(name = "release_date", nullable = false)
    private Date releaseDate;

    @Column(name = "rating_date")
    private Date ratingDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "album_rating")
    private AlbumRating albumRating;

    @Column(name = "album_cover", length = 16777215)
    private byte[] albumCover;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
