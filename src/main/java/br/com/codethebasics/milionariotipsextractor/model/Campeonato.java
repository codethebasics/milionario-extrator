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
public class Campeonato {

    @JsonProperty("Descricao")
    private String descricao;

    @JsonProperty("Mercado")
    private String mercado;

    @JsonProperty("Lines")
    private Line[] lines;

}
