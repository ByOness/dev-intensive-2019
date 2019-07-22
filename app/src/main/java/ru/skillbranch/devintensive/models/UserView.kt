package ru.skillbranch.devintensive.models

class UserView (
    var id:String,
    val fullName:String,
    val nickName:String,
    var avatar:String? = null,
    var status:String? = "offline",
    val initials:String?
) {
    fun prinMe() {
        println("""
           id:$id,
           fullName:$fullName
           nickName$nickName
           avatar:$avatar
           status:$status
           initials$initials
        """.trimIndent())
    }
}