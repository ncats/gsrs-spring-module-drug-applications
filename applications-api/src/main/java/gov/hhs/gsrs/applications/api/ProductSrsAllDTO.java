package gov.hhs.gsrs.applications.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductSrsAllDTO {

    private String id;
    private String applicationId;
    private List<ProductNameSrsAllDTO> applicationProductNameList = new ArrayList<>();
    private List<AppIngredientAllDTO> applicationIngredientList = new ArrayList<>();
}
