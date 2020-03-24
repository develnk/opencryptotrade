package com.opencryptotrade.templatebuilder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "email_template")
public class EmailTemplate  implements Serializable  {

    private static final long serialVersionUID = 3328044920395369472L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    @Setter @Getter
    @Column(length = 64)
    private String name;

    @Setter @Getter
    private String subject;

    @Lob
    @Setter @Getter
    private String body;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "folder_id")
    @Setter @Getter
    private Folder folder;

    @Setter @Getter
    private String trigger;

}
