package me.jollypanda.ormcomparator.cupboard

import me.jollypanda.ormcomparator.interfaces.IStudent

/**
 * Model for Cupboard.
 *
 * @author Yamushev Igor
 * @since  04.10.16
 */
open class CupboardStudentModel : IStudent {

    open var _id: Long? = null
    open var name: String = ""
    open var surname: String = ""
    open var age: Int = 0
    open var address: String = ""
    open var phone: String = ""
    open var groupNumber: Int = 0

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