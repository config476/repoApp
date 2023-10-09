package com.rahulp.test.model;

import java.util.Date;

public class Employee {

  private int id;
  private String name;
  private String location;
  private String branch;

  public String getSalary() {
    return salary;
  }

  public void setSalary(String salary) {
    this.salary = salary;
  }

  private String salary;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLocation() {
    return location;
  }


  public void setLocation(String location) {
    this.location = location;
  }

  public String getBranch() {
    return branch;
  }

  public void setBranch(String branch) {
    this.branch = branch;
  }

//  @Override
//  public String toString() {
//    return "Employee{" +
//        "id=" + id +
//        ", name='" + name + '\'' +
//        ", location='" + location + '\'' +
//        ", branch='" + branch + '\'' +
//        '}';
//  }
}
