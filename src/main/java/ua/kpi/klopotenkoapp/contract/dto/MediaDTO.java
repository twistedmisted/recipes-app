package ua.kpi.klopotenkoapp.contract.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;

@Data
@EqualsAndHashCode
public class MediaDTO {

    private Long id;
    private String filename;
    private InputStream inputStream;
}
