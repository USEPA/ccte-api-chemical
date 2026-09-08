package gov.epa.ccte.api.chemical.web.rest;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BatchMsReadyMassForm {

    @NotEmpty(message = "Masses couldn't be empty")
    private List<@NotNull(message = "masses must not contain null values.") Double> masses;
    @NotNull(message = "error value couldn't be null")
    private Integer error;
}
