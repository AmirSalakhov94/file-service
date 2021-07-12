package tech.itpark.fileservice.security.principal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;
import tech.itpark.fileservice.dto.ProfileDto;
import tech.itpark.fileservice.dto.enums.Role;
import tech.itpark.fileservice.exception.ExtractPrincipalException;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProfilePrincipalExtractor {

    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String CREATE_DATE = "createDate";
    private static final String EMAIL = "email";
    private static final String PHONE = "phone";

    private final TokenStore tokenStore;

    public ProfileDto extractPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (OAuth2Authentication.class.isAssignableFrom(authentication.getClass())) {
            OAuth2Authentication oAuth2Authentication
                    = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
            OAuth2AuthenticationDetails details =
                    (OAuth2AuthenticationDetails) oAuth2Authentication.getDetails();

            OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(details.getTokenValue());
            Map<String, Object> additionalInformation = oAuth2AccessToken.getAdditionalInformation();
            UUID id = UUID.fromString(additionalInformation.get(ID).toString());
            String name = (String) additionalInformation.get(NAME);
            String email = (String) additionalInformation.get(EMAIL);
            String phone = (String) additionalInformation.get(PHONE);
            Instant createDate = Instant.ofEpochMilli((Long) additionalInformation.get(CREATE_DATE));

            return ProfileDto.builder()
                    .id(id)
                    .name(name)
                    .role(Role.PROFILE)
                    .email(email)
                    .phone(phone)
                    .createDate(createDate)
                    .build();
        }

        throw new ExtractPrincipalException("Fail extract principal");
    }
}
