package ua.kpi.klopotenkoapp.contract.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeUserDTO {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String email;

    public RecipeUserDTO(String email) {
        this.email = email;
    }
}
