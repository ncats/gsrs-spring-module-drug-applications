package gov.nih.ncats.application.services;

import gov.nih.ncats.application.models.Application;
import gov.nih.ncats.application.repositories.ApplicationRepository;

import gsrs.module.substance.autoconfigure.GsrsSubstanceModuleAutoConfiguration;
import gsrs.module.substance.SubstanceEntityService;
import gsrs.service.ExportService;
import ix.ginas.models.v1.Substance;
import gsrs.module.substance.SubstanceEntityServiceImpl;

import gsrs.controller.IdHelpers;
import gsrs.events.AbstractEntityCreatedEvent;
import gsrs.events.AbstractEntityUpdatedEvent;

// import gsrs.module.substance.events.SubstanceCreatedEvent;
// import gsrs.module.substance.events.SubstanceUpdatedEvent;
// import gsrs.module.substance.repository.SubstanceRepository;
import gsrs.repository.GroupRepository;
import gsrs.service.AbstractGsrsEntityService;
import gsrs.validator.ValidatorConfig;
import ix.core.validator.GinasProcessingMessage;
import ix.core.validator.ValidationResponse;
import ix.core.validator.ValidationResponseBuilder;
import ix.core.validator.ValidatorCallback;
// import ix.ginas.models.v1.Substance;
// import ix.ginas.utils.GinasProcessingStrategy;
// import ix.ginas.utils.JsonSubstanceFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// import springfox.documentation.spring.web.json.Json;
import ix.utils.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class SubstanceModuleService {

    @Autowired
    protected SubstanceEntityService substanceEntityService;

    public Optional<Substance> getSubstanceDetails(String someKindOfId) {

        Optional<Substance> substance = this.substanceEntityService.flexLookup(someKindOfId);

        return substance;
    }

    public SubstanceEntityService getSubstanceEntityService() {
        return substanceEntityService;
    }
}

