package gov.hhs.gsrs.applications.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import gsrs.api.AbstractLegacySearchGsrsEntityRestTemplate;
import gsrs.api.GsrsEntityRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ApplicationsApi extends AbstractLegacySearchGsrsEntityRestTemplate<ApplicationAllDTO, String> {
    public ApplicationsApi(RestTemplateBuilder restTemplateBuilder, String baseUrl, ObjectMapper mapper) {
        super(restTemplateBuilder, baseUrl, "applicationsall", mapper);
    }

    @Override
    protected ApplicationAllDTO parseFromJson(JsonNode node) {
        return getObjectMapper().convertValue(node, ApplicationAllDTO.class);
    }

    @Override
    protected String getIdFrom(ApplicationAllDTO dto) {
        return dto.getId();
    }

}