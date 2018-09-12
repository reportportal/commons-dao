package com.epam.ta.reportportal.entity.Attribute;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Andrey Plisunov
 */
@Entity
@Table(name = "attribute")
public class Attribute implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
