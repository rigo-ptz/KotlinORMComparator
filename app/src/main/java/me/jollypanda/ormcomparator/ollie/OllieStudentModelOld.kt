package me.jollypanda.ormcomparator.ollie

import me.jollypanda.ormcomparator.interfaces.IStudent

/**
 * Model for Ollie.
 *
 * @author Yamushev Igor
 * @since  03.10.16
 */
//@Table("tbl_ollie_students")
class OllieStudentModelOld : IStudent {

    var name: String = ""
    var surname: String = ""
    var age: Int = 0
    var address: String = ""
    var phone: String = ""
    var groupNumber: Int = 0

    override fun setStudentNameAndSurname(name: String, surname: String) {
        this.name = name
        this.surname = surname
    }

    override fun setStudentAge(age: Int) {
        this.age = age
    }

    override fun setStudentAddress(address: String) {
        this.address = address
    }

    override fun setStudentPhone(phone: String) {
        this.phone = phone
    }

    override fun setStudentGroupNum(number: Int) {
        this.groupNumber = number
    }
}