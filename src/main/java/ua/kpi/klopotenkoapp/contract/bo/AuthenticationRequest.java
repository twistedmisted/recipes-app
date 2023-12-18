package ua.kpi.klopotenkoapp.contract.bo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AuthenticationRequest {

    @NotNull
    @Size(min = 5, max = 50)
    private String login;

    @NotNull
//    @Size(min = 10, max = 20)
    private String password;

}
