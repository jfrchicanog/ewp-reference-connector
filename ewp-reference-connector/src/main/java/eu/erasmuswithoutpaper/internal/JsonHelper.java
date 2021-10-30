package eu.erasmuswithoutpaper.internal;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    
    private JsonHelper(){}
    
    public static <T> T mapToObject(Class<T> c, String json) throws IOException {
    
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(dateFormat);

        //JSON from file to Object
        T object = mapper.readValue(json, c);

        return object;
    }
    
    public static String objectToJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(dateFormat);
        
        return mapper.writeValueAsString(object);
    }
}
