package com.kt.smartfarm.service.UltraShortWeatherVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

@Getter
@Setter
@NoArgsConstructor
public class UltraShortWeatherVOHeader {
    @JsonProperty("resultCode")
    private String resultCode;
    @JsonProperty("resultMsg")
    private String resultMsg;
}
