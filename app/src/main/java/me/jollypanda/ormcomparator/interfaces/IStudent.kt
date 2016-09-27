package me.jollypanda.ormcomparator.interfaces

/**
 * Interface for all student models
 *
 * @author Yamushev Igor
 * @since  24.09.16
 */
interface IStudent {
    fun setStudentNameAndSurname(name: String, surname: String)
    fun setStudentAge(age: Int)
    fun setStudentAddress(address: String)
    fun setStudentPhone(phone: String)
    fun setStudentGroupNum(number: Int)
}