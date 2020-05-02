package com.kieranheg.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class JacksonMapper_UT {

    @Test
    @DisplayName("Test serialize - success")
    public void serialize() throws  IOException {
        List<Header> headers = new ArrayList<>();
        Header hdr1 = new Header("Hdr1", "Value1");
        Header hdr2 = new Header("Hdr2", "Value2");
        headers.add(hdr1);
        headers.add(hdr2);
        ObjectMapper mapper = new ObjectMapper();

        String json = mapper.writeValueAsString(headers);
        assertThat(json).isEqualTo("[{\"name\":\"Hdr1\",\"value\":\"Value1\"},{\"name\":\"Hdr2\",\"value\":\"Value2\"}]");
    }

    @Test
    @DisplayName("Test deserialize - success")
    public void deserialize() throws  IOException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType mapCollectionType = mapper.getTypeFactory()
                .constructCollectionType(List.class, Header.class);

        String json = "[{\"name\":\"Hdr1\",\"value\":\"Value1\"},{\"name\":\"Hdr2\",\"value\":\"Value2\"}]";
        List<Header> value = mapper.readValue(json, mapCollectionType);

        assertThat(value.get(0).getName()).isEqualTo("Hdr1");
        assertThat(value.get(0).getValue()).isEqualTo("Value1");
        assertThat(value.get(1).getName()).isEqualTo("Hdr2");
        assertThat(value.get(1).getValue()).isEqualTo("Value2");
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
