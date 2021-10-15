package gov.hhs.gsrs.applications.api;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationAllDTO {

    private String id;
    private String center;
    private String appType;
    private String appNumber;
    private String title;
    private String sponsorName;
    private String appSubType;
    private String appStatus;
    private String divisionClassDesc;
    private String provenance;
    private String fromTable;

    private List<ProductSrsAllDTO> applicationProductList = new ArrayList<>();
    private List<AppIndicationAllDTO> indicationList = new ArrayList<>();
}

