package me.jollypanda.ormcomparator.dbflow

import com.raizlabs.android.dbflow.annotation.Column
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.structure.BaseModel
import me.jollypanda.ormcomparator.interfaces.IStudent

/**
 * Model for DBFlow.
 *
 * @author Yamushev Igor
 * @since  03.10.16
 */
@Table(database = KotlinDatabase::class)
class DBFlowStudentModel() : BaseModel(), IStudent {

    @PrimaryKey(autoincrement = true) var id: Int = 0
    @Column var name: String = ""
    @Column var surname: String = ""
    @Column var age: Int = 0
    @Column var address: String = ""
    @Column var phone: String = ""
    @Column var groupNumber: Int = 0

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