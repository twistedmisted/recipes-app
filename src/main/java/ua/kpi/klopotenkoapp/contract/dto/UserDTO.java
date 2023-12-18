package ua.kpi.klopotenkoapp.contract.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonRootName(value = "user")
public class UserDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "The name must be between 2 and 50 symbols")
    private String name;

    @NotBlank(message = "Surname is mandatory")
    @Size(min = 2, max = 50, message = "The surname must be between 2 and 50 symbols")
    private String surname;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 2, max = 50, message = "The username must be between 2 and 50 symbols")
    private String username;

    @NotBlank(message = "Name is mandatory")
    @Email(message = "This is not an email")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 2, max = 50, message = "The password must be between 10 and 20 symbols")
    private String password;

    @JsonIgnore
    private Boolean verified;
}
