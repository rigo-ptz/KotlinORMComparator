package me.jollypanda.ormcomparator.orm_lite

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import me.jollypanda.ormcomparator.interfaces.IStudent

/**
 * Model for OrmLite
 *
 * @author Yamushev Igor
 * @since  26.09.16
 */
@DatabaseTable(tableName = "tbOrmLite")
open class OrmLiteStudentModel : IStudent {

    @DatabaseField(generatedId = true)
    open var id: Int = 0

    @DatabaseField
    open var name: String = ""

    @DatabaseField
    open var surname: String = ""

    @DatabaseField
    open var age: Int = 0

    @DatabaseField
    open var address: String = ""

    @DatabaseField
    open var phone: String = ""

    @DatabaseField
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