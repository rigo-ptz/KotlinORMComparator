package me.jollypanda.ormcomparator.ollie;

import org.jetbrains.annotations.NotNull;

import me.jollypanda.ormcomparator.interfaces.IStudent;
import ollie.Model;
import ollie.annotation.AutoIncrement;
import ollie.annotation.Column;
import ollie.annotation.PrimaryKey;
import ollie.annotation.Table;

/**
 * Model for Ollie
 *
 * @author Yamushev Igor
 * @since 03.10.16
 */
@Table("tbl_ollie_students")
public class OllieStudentModel extends Model implements IStudent {

    @Column("name")
    public String name;

    @PrimaryKey
    @AutoIncrement
    public Long id;

    @Column("surname")
    public String surname;

    @Column("age")
    public Integer age;

    @Column("address")
    public String address;

//    @Column("phone")
//    public String phone;

    @Column("group_num")
    public Integer groupNumber;

    @Override
    public void setStudentNameAndSurname(@NotNull String name, @NotNull String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public void setStudentAge(int age) {
        this.age = age;
    }

    @Override
    public void setStudentAddress(@NotNull String address) {
        this.address = address;
    }

    @Override
    public void setStudentPhone(@NotNull String phone) {
//        this.phone = phone;
    }

    @Override
    public void setStudentGroupNum(int number) {
        this.groupNumber = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(Integer groupNumber) {
        this.groupNumber = groupNumber;
    }
}
