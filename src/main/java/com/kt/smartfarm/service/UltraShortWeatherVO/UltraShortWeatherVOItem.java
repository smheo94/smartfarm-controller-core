package com.kt.smartfarm.service.UltraShortWeatherVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class UltraShortWeatherVOItem {
    @JsonProperty("baseDate")
    private String baseDate;
    @JsonProperty("baseTime")
    private String baseTime;
    @JsonProperty("category")
    private String category;
    @JsonProperty("nx")
    private Integer nx;
    @JsonProperty("ny")
    private Integer ny;
    @JsonProperty("obsrValue")
    private Double obsrValue;
}
