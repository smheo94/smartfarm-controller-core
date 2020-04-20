package com.kt.smartfarm.service.UltraShortWeatherVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class UltraShortWeatherVOBody {
    @JsonProperty("dataType")
    private String dataType;
    @JsonProperty("items")
    private UltraShortWeatherVOItems items;
    @JsonProperty("pageNo")
    private Integer pageNo;
    @JsonProperty("numOfRows")
    private Integer numOfRows;
    @JsonProperty("totalCount")
    private Integer totalCount;
}
