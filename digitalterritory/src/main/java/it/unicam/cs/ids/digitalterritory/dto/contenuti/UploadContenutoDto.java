package it.unicam.cs.ids.digitalterritory.dto.contenuti;

import it.unicam.cs.ids.digitalterritory.db.enums.TipoContenuto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UploadContenutoDto(TipoContenuto tipoContenuto, String textContent, String fileContent, UUID poiId, boolean isPrivato) {
}
