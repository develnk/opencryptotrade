package com.opencryptotrade.templatebuilder.entity;

import com.opencryptotrade.templatebuilder.enums.BaseBlockType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "base_block")
public class BaseBlock implements Serializable {
    private static final long serialVersionUID = 3373864920395369472L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    @Getter
    private Long id;

    @Column(columnDefinition = "smallint")
    @Enumerated(EnumType.ORDINAL)
    @Setter @Getter
    private BaseBlockType type;

    @Lob
    @Setter @Getter
    private String html;
}
