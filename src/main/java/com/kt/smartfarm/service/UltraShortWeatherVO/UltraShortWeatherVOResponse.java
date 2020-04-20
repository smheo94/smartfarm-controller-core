package com.kt.smartfarm.service.UltraShortWeatherVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class UltraShortWeatherVOResponse {
    @JsonProperty("header")
    private UltraShortWeatherVOHeader header;
    @JsonProperty("body")
    private UltraShortWeatherVOBody body;
}
