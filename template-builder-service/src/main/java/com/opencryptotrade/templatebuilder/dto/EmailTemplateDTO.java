package com.opencryptotrade.templatebuilder.dto;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplateDTO  implements Serializable {

    @Setter @Getter
    @Valid
    private String id;

    @Setter @Getter
    @Valid
    @NotNull
    private String name;

    @Setter  @Getter
    @Valid
    @NotNull
    private String subject;

    @Setter @Getter
    @Valid
    @NotNull
    private String folder;

    @Setter @Getter
    @Valid
    @NotNull
    private Integer trigger;

    @Setter @Getter
    @Valid
    @NotNull
    private Set<BaseBlockLinkDTO> baseBlockLinks;

}
