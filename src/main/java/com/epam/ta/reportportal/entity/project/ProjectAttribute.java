package com.epam.ta.reportportal.entity.project;

import com.epam.ta.reportportal.entity.enums.ProjectAttributeEnum;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Andrey Plisunov
 */
@Entity
@Table(name = "project_attribute")
public class ProjectAttribute implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private ProjectAttributeEnum name;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectAttributeEnum getName() {
        return name;
    }

    public void setName(ProjectAttributeEnum name) {
        this.name = name;
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
