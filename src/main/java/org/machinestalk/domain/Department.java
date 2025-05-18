package org.machinestalk.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("departments")
public class Department {

  @Id
  private Long id;

  private String name;

  public Department() {
  }

  public Department(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Department{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }
}
