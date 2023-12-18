package ua.kpi.klopotenkoapp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Setter
@Getter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "surname", nullable = false)
    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @Column(name = "username", nullable = false)
    @NotBlank(message = "Username is mandatory")
    private String username;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @Column(name = "verified", nullable = false)
    @NotNull(message = "Verified is mandatory")
    private Boolean verified;

    @OneToMany(mappedBy = "user", fetch = LAZY)
    private List<RecipeEntity> recipes = new ArrayList<>();
}
