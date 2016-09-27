package me.jollypanda.ormcomparator.utils

import me.jollypanda.ormcomparator.interfaces.IStudent

/**
 * Generates collections for tests.
 *
 * @author Yamushev Igor
 * @since  24.09.16
 */

fun <Student : IStudent> createStudentsCollection(studentClass: Class<Student>, count: Int): Collection<Student> {
    var collection = mutableListOf<Student>()

    for (i in 0 .. count - 1) {
        val studentModel: Student
        try {
            studentModel = studentClass.newInstance()
            studentModel.setStudentNameAndSurname("Fedor$i", "Fedorov$i")
            studentModel.setStudentAge(i)
            studentModel.setStudentAddress("Address is very big string $i")
            studentModel.setStudentGroupNum(22000 + i)
            collection.add(studentModel)
        } catch (e:InstantiationException) {
            e.printStackTrace()
        }
        catch (e:IllegalAccessException) {
            e.printStackTrace()
        }

    }
    return collection
}
