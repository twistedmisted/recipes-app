package ua.kpi.klopotenkoapp.contract.dto;

import lombok.Data;

@Data
public class SuccessfulMessageInfo {
    private String to;
    private String fullName;
    private String recipeName;
    private String url;
}
