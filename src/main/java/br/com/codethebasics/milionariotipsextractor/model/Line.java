package br.com.codethebasics.milionariotipsextractor.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class Line {

    @JsonProperty("Hora")
    private Integer hora;

    @JsonProperty("Cells")
    private Jogo[] jogos;

    @JsonProperty("Percents")
    private Integer percents;
}
