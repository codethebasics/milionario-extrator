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
public class Jogo {

    @JsonProperty("BorderColor")
    private String borderColor;

    @JsonProperty("Countable")
    private boolean countable;

    @JsonProperty("Result")
    private String result;

    @JsonProperty("IsGreen")
    private boolean isGreen;

    @JsonProperty("Tooltip")
    private String tooltip;

    @JsonProperty("SumScore")
    private Integer sumScore;
}
