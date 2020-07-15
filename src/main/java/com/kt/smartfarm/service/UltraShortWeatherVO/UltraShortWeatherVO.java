package com.kt.smartfarm.service.UltraShortWeatherVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UltraShortWeatherVO {
    @JsonProperty("response")
    private UltraShortWeatherVOResponse response;

    public enum CategoryEnum {
        PTY("pty"),
        REH("reh"),
        RN1("rn1"),
        T1H("t1h"),
        UUU("uuu"),
        VEC("vec"),
        VVV("vvv"),
        WSD("wsd");
        private String category;
        CategoryEnum(String pty) {
            this.category = pty;
        }
        public String getCategory(){
            return this.category;
        }


    }

    public boolean isRequestSuccess(){
        return response.getHeader().getResultCode().equals("00");
    }

    public LinkedHashMap<String, Object> ultraOjbToMap(String baseTime){
        LinkedHashMap<String, Object> hm = new LinkedHashMap<String, Object>();
        List<UltraShortWeatherVOItem> items = response.getBody().getItems().getItem();

        hm.put("base_date", baseTime);
        hm.put("base_time", items.get(0).getBaseTime());
        hm.put("nx",items.get(0).getNx());
        hm.put("ny", items.get(0).getNy());
        for(UltraShortWeatherVOItem item : items){
            CategoryEnum catEnum = CategoryEnum.valueOf(item.getCategory());
            hm.put(catEnum.getCategory(), item.getObsrValue());
        }
        return hm;
    }

}

