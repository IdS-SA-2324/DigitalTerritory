package it.unicam.cs.ids.digitalterritory.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unicam.cs.ids.digitalterritory.dto.OsmResponse;
import it.unicam.cs.ids.digitalterritory.dto.osmdetails.OsmDetails;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class OsmService {
    private static final String OSM_SEARCH_URL
            = "https://nominatim.openstreetmap.org/search?city=%s&county=%s&country=italy&polygon_geojson=1&format=jsonv2";
    private static final String OSM_DETAILS_URL
            = "https://nominatim.openstreetmap.org/details.php?osmtype=%s&osmid=%s&addressdetails=1&hierarchy=0&group_hierarchy=1&polygon_geojson=1&format=json";
    private static final ObjectMapper mapper = new ObjectMapper();
    public OsmResponse getComuneByNomeRegione(String nome, String regione) throws Exception {
        if (nome.isEmpty() || regione.isEmpty()) {
            throw new Exception("Compilare tutti i campi");
        }
        String url = String.format(OSM_SEARCH_URL, nome, regione);
        String json = makeHttpCall(url);
        TypeReference<List<OsmResponse>> typeReference = new TypeReference<>() {};
        List<OsmResponse> list = mapper.readValue(json, typeReference);
        return list.get(0);
    }
    public OsmDetails getComuneById(String id) throws Exception {
        if(id.isEmpty()) {
            throw new Exception("Compilare tutti i campi");
        }
        String url = String.format(OSM_DETAILS_URL, id.charAt(0), id.substring(1));
        String json = makeHttpCall(url);
        return mapper.readValue(json, OsmDetails.class);
    }

    private static String makeHttpCall(String url) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        if(response.getStatusCode() == HttpStatusCode.valueOf(200) && response.hasBody()) {
            return response.getBody();
        }
        return "";
    }
}
