package com.opencryptotrade.templatebuilder.dto;

import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderDTO implements Serializable {

    @Setter @Getter
    private String id;

    @Setter @Getter
    private String name;

}
