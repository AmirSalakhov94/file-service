package tech.itpark.fileservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import tech.itpark.fileservice.dto.ProfileDto;

import java.util.Base64;

@UtilityClass
public class ProfileUtil {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static ProfileDto getProfileWithHeader(String xProfile) {
        return objectMapper.readValue(new String(Base64.getDecoder().decode(xProfile)), ProfileDto.class);
    }
}
