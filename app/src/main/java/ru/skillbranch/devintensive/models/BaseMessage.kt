package ru.skillbranch.devintensive.models

import java.util.*

class Chat (
    val id:String,
    val members: MutableList<User> = mutableListOf(),
    val messages: MutableList<BaseMessage> = mutableListOf()
) {
}

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var lastId = -1
        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date = Date(),
            type: String = "text",
            payload: Any?
        ): BaseMessage {
            return when (type) {
                "image" -> ImageMessage("${++lastId}", from, chat, date = date, image = payload as String)
                else -> TextMessage("${++lastId}", from, chat, date = date, text = payload as String)
            }
        }
    }
}

class TextMessage(id: String, from: User?, chat: Chat, isIncoming: Boolean= false, date: Date, var text: String?) :
    BaseMessage(id, from, chat, isIncoming, date) {
    override fun formatMessage(): String {
        return "$id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"} сообщение"
    }
}

class ImageMessage(id: String, from: User?, chat: Chat, isIncoming: Boolean = false, date: Date, var image: String) :
    BaseMessage(id, from, chat, isIncoming, date) {
    override fun formatMessage(): String {
        return "id:$id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"} изображение"
    }
}