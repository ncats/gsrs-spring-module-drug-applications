package gov.hhs.gsrs.applications.api;

import tools.jackson.databind.JsonNode;
import gsrs.api.AbstractLegacySearchGsrsEntityRestTemplate;
import org.springframework.boot.restclient.RestTemplateBuilder;
import tools.jackson.databind.json.JsonMapper;

public class ApplicationsApi extends AbstractLegacySearchGsrsEntityRestTemplate<ApplicationAllDTO, String> {

    private final JsonMapper mapper = JsonMapper.builderWithJackson2Defaults().build();

    public ApplicationsApi(RestTemplateBuilder restTemplateBuilder, String baseUrl, JsonMapper mapper) {
        super(restTemplateBuilder, baseUrl, "applicationsall", mapper);
    }

    @Override
    protected ApplicationAllDTO parseFromJson(JsonNode node) {
        return mapper.convertValue(node, ApplicationAllDTO.class);
    }

    @Override
    protected String getIdFrom(ApplicationAllDTO dto) {
        return dto.getId();
    }

}