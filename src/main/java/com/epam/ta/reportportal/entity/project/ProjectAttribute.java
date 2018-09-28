package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.attribute.Attribute;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Andrey Plisunov
 */
@Entity
@Table(name = "project_attribute")
@IdClass(ProjectAttributeKey.class)
public class ProjectAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @Column(name = "value")
    private String value;

    @Id
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
