package com.kieranheg.utils;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static com.kieranheg.utils.JacksonMapperHelper.asJsonContentFromJsonString;
import static com.kieranheg.utils.JacksonMapperHelper.asJsonString;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class JacksonMapper_UT {
    private static final String EXPECTED_JSON = "[{\"name\":\"Hdr1\",\"value\":\"Value1\"},{\"name\":\"Hdr2\",\"value\":\"Value2\"}]";
    private static final String HDR_1 = "Hdr1";
    private static final String HDR_2 = "Hdr2";
    private static final String VALUE_1 = "Value1";
    private static final String VALUE_2 = "Value2";
    
    @Test
    @DisplayName("Test serialize - success")
    public void serialize() {
        Header header1 = Header.builder().name(HDR_1).value(VALUE_1).build();
        Header header2 = Header.builder().name(HDR_2).value(VALUE_2).build();
    
        assertThat(asJsonString(asList(header1, header2))).isEqualTo(EXPECTED_JSON);
    }

    @Test
    @DisplayName("Test deserialize - success")
    public void deserialize() throws IOException {
        List<Header> value = asJsonContentFromJsonString(EXPECTED_JSON);
        
        assertThat(value.get(0).getName()).isEqualTo(HDR_1);
        assertThat(value.get(0).getValue()).isEqualTo(VALUE_1);
        assertThat(value.get(1).getName()).isEqualTo(HDR_2);
        assertThat(value.get(1).getValue()).isEqualTo(VALUE_2);
    }
}

