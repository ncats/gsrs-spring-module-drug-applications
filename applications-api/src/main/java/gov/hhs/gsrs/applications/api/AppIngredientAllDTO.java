package gov.hhs.gsrs.applications.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class AppIngredientAllDTO {

    private String id;
    private String productId;
    private String substanceKey;
    private String ingredientType;
    private String applicantIngredName;
}
