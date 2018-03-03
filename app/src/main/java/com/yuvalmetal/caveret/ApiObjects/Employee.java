package com.yuvalmetal.caveret.ApiObjects;

/**
 * Created by yuvalmetal on 27/10/2017.
 */

public class Employee {
    public int Id;
    public String FirstName;
    public String LastName ;
    public long PhoneNumber ;
    public Permission EmployeePermission ;
    public  Role EmployeeRole;
    public String UserName;
    public String Password;


    public Employee(int id, String firstName, String lastName, long phoneNumber, Permission permission, Role employeeRole, String userName, String password) {
        Id = id;
        FirstName = firstName;
        LastName = lastName;
        PhoneNumber = phoneNumber;
        EmployeePermission = permission;
        EmployeeRole = employeeRole;
        UserName = userName;
        Password = password;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public long getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public Permission getPermission() {
        return EmployeePermission;
    }

    public void setPermission(Permission permission) {
        EmployeePermission = permission;
    }

    public Role getEmployeeRole() {
        return EmployeeRole;
    }

    public void setEmployeeRole(Role employeeRole) {
        EmployeeRole = employeeRole;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
