package me.jollypanda.ormcomparator.green_dao

import me.jollypanda.ormcomparator.interfaces.IStudent
import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id

/**
 * Model for GreenDAO
 *
 * @author Yamushev Igor
 * @since  29.09.16
 */
@Entity
open class GreenDaoStudentModelOld : IStudent {

    @Id
    open var id: Int = 0
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