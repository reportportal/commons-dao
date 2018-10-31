package com.epam.ta.reportportal.entity.attribute;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Attribute attribute = (Attribute) o;
		return Objects.equals(name, attribute.name);
	}

	@Override
	public int hashCode() {

		return Objects.hash(name);
	}
}
