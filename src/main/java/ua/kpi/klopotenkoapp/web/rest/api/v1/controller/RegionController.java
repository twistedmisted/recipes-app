package ua.kpi.klopotenkoapp.web.rest.api.v1.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.kpi.klopotenkoapp.service.facade.RecipeFacade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/regions")
@RequiredArgsConstructor
@Slf4j
public class RegionController {

    private final RecipeFacade recipeFacade;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllRegions() {
        log.debug("Getting all regions");
        List<String> regions = recipeFacade.getAllRegions();
        Map<String, Object> response = new HashMap<>();
        response.put("regions", regions);
        return new ResponseEntity<>(response, OK);
    }
}
