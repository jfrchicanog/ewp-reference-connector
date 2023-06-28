/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eu.erasmuswithoutpaper.common.boundary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.johnzon.mapper.JohnzonAny;
import org.apache.johnzon.mapper.JohnzonIgnore;

/**
 *
 * @author Moritz Baader
 */
public class ParamsClass {

    @JohnzonIgnore
    private Map<String, List<String>> unknownFields = new HashMap<String, List<String>>();

    @JohnzonAny
    public Map<String, Object> getAny() {
        Map<String, Object> re = new HashMap<>();
        for (String key : unknownFields.keySet()) {
            re.put(key, unknownFields.get(key));
        }
        return re;
    }

    @JohnzonAny
    public void handle(final String key, final Object val) {
        if (val instanceof List) {
            List<Object> list = (List<Object>) val;
            List<String> params = new ArrayList<>();
            for (Object o : list) {
                if (o instanceof String) {
                    params.add((String) o);
                }
            }
            this.unknownFields.put(key, params);
        } else if (val instanceof String) {
            List<String> params = new ArrayList<>();
            params.add((String) val);
            this.unknownFields.put(key, params);
        }
    }

    public Map<String, List<String>> getUnknownFields() {
        return unknownFields;
    }

    public void setUnknownFields(Map<String, List<String>> unknownFields) {
        this.unknownFields = unknownFields;
    }
    
    
}
