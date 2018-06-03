package model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name="user",
        uniqueConstraints={@UniqueConstraint(columnNames={"username"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
final public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, unique = true, length = 5)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true, length = 10)
    private String username;

    @Column(name = "password", nullable = false, unique = false, length = 150)
    private String password;
}
