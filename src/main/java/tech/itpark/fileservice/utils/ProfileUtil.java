package tech.itpark.fileservice.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import tech.itpark.fileservice.dto.ProfileDto;
import tech.itpark.fileservice.dto.TokenVerificationResponse;

import java.util.Base64;

@UtilityClass
public class ProfileUtil {

    private static final TypeReference<TokenVerificationResponse<ProfileDto>> TYPE_REFERENCE = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public static ProfileDto getProfileWithHeader(String xProfile) {
        TokenVerificationResponse<ProfileDto> tokenVerification
                = objectMapper.readValue(new String(Base64.getDecoder().decode(xProfile)), TYPE_REFERENCE);
        return tokenVerification.getPayload();
    }
}
