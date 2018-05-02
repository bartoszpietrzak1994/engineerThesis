package model.album;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Date;

@Entity
@Table(name="album",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
final public class Album
{
    private Long id;
    private String artist;
    private String title;
    private Date releaseDate;
    private Date ratingDate;
    private byte[] albumCover;
}
