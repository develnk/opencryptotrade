package com.opencryptotrade.templatebuilder.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value={ "templateId", }, allowSetters = true)
public class BaseBlockLinkDTO implements Serializable {

    @Setter @Getter
    @Valid
    private String id;

    @Setter @Getter
    @Valid
    @NotNull
    private short weight;

    @Setter @Getter
    @Valid
    @NotNull
    private String baseBlockId;

    @Setter @Getter
    @Valid
    private String templateId;

    @Setter @Getter
    @Valid
    private String baseBlockCopyId;

    @Setter @Getter
    @Valid
    @NotNull
    private boolean editFlag;

    @Setter @Getter
    private String html;

}
