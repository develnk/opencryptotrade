package com.opencryptotrade.templatebuilder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "folder")
public class Folder implements Serializable {

    private static final long serialVersionUID = 3373812230395369472L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Setter @Getter
    private Long id;

    @Column
    @Setter	@Getter
    private String name;

    @Setter	@Getter
    @OneToOne(optional = false, mappedBy = "folder", cascade = CascadeType.ALL)
    private EmailTemplate template;

}
