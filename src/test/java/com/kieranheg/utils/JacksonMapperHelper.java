package com.kieranheg.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

public class JacksonMapperHelper {
    private static ObjectMapper mapper = new ObjectMapper();
    
    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static List<Header> asJsonContentFromJsonString(final String json) {
        CollectionType mapCollectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, Header.class);
        try {
            return mapper.readValue(json, mapCollectionType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
    }
}

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Header {
    @NotNull
    private String name;
    
    @NotNull
    private String value;
}