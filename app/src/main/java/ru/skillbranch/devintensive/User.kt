package ru.skillbranch.devintensive

import java.util.*

data class User(val id : String,
                var firstName : String?,
                var lastName : String?,
                var avatar : String?,
                var rating : Int = 0,
                var respect : Int = 0,
                var lastVisit : Date? = Date(),
                var isOnline : Boolean = false) {

    companion object Factory {
        var userId:Int = -1
        fun makeUser(fullName:String?): User{
            userId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User("$userId", firstName, lastName, null)
        }
    }
}