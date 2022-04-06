package br.edu.utfpr.pb.pw26s.server.model;

import br.edu.utfpr.pb.pw26s.server.annotation.UniqueUsername;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User {

    @Id
    @GeneratedValue
    private long id;

    @NotNull(message = "{br.edu.utfpr.pw26s.user.username.constraints.Null.message}")
    @Size(min = 4, max = 255, message = "{br.edu.utfpr.pw26s.user.username.constraints.Size.message}")
    @UniqueUsername
    private String username;

    @NotNull
    @Size(min = 4, max = 255)
    private String displayName;

    @NotNull
    @Size(min = 6, max = 255)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "{br.edu.utfpr.pw26s.user.password.constraints.Pattern.message}")
    private String password;

}
