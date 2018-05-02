package model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="user",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
final public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true, length = 5)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 10)
    private String userName;

    @Column(name = "password", nullable = false, unique = false, length = 10)
    private String password;
}
