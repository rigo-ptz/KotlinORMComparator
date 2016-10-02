package me.jollypanda.ormcomparator.green_dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.jetbrains.annotations.NotNull;

import me.jollypanda.ormcomparator.interfaces.IStudent;

/**
 * Model for GreenDAO.
 *
 * "Note, that only Java classes are supported.
 * If you prefer another language like Kotlin, your entity classes must still be Java.'
 * @link http://greenrobot.org/greendao/documentation/updating-to-greendao-3-and-annotations/#greenDAO_3_Entities_and_Annotations
 *
 * @author Yamushev Igor
 * @since 01.10.16
 */
@Entity
public class GreenDaoStudentModel implements IStudent {

    private long id;

    private String name;

    private String surname;

    private int mAge;

    private String address;

    private String phone;

    private int groupNumber;

    @Generated(hash = 493374370)
    public GreenDaoStudentModel(long id, String name, String surname, int mAge, String address, String phone, int groupNumber) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mAge = mAge;
        this.address = address;
        this.phone = phone;
        this.groupNumber = groupNumber;
    }

    @Generated(hash = 1814834759)
    public GreenDaoStudentModel() {
    }

    @Override
    public void setStudentNameAndSurname(@NotNull String name, @NotNull String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public void setStudentAge(int age) {
        mAge = age;
    }

    @Override
    public void setStudentAddress(@NotNull String address) {
        this.address = address;
    }

    @Override
    public void setStudentPhone(@NotNull String phone) {
        this.phone = phone;
    }

    @Override
    public void setStudentGroupNum(int number) {
        this.groupNumber = number;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getMAge() {
        return this.mAge;
    }

    public void setMAge(int mAge) {
        this.mAge = mAge;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getGroupNumber() {
        return this.groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

}
