package ua.kpi.klopotenkoapp.contract.dto;

import lombok.Builder;
import lombok.Data;
import ua.kpi.klopotenkoapp.contract.util.StaffAccessLevel;

@Data
@Builder
public class StaffDTO {

    private Long id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private StaffAccessLevel accessLevel;
}
