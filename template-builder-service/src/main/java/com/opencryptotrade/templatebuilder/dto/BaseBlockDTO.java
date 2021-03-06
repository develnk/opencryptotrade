package com.opencryptotrade.templatebuilder.dto;

import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseBlockDTO implements Serializable, Comparable<BaseBlockDTO> {

    @Setter @Getter
    @Valid
    private String id;

    @Setter @Getter
    @Valid
    @NotNull
    private BaseBlockType type;

    @Setter @Getter
    @Valid
    @NotNull
    private String html;

    @Override
    public int compareTo(BaseBlockDTO o) {
        int compareType = o.getType().ordinal();
        return this.getType().ordinal() - compareType;
    }

}
