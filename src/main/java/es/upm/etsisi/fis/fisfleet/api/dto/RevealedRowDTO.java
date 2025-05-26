package es.upm.etsisi.fis.fisfleet.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class RevealedRowDTO {
    @Builder.Default
    private String type = "REVEALED_ROW";
    private String content;
}
