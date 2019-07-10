package ru.skillbranch.devintensive.models

import java.util.*

class Chat(
    val id: String,
    val members: MutableList<User> = mutableListOf(),
    val messages: MutableList<BaseMessage> = mutableListOf()
)

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var messageId: Int = -1
        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date,
            type: String = "text",
            payload: Any?,
            isIncoming: Boolean = false
        ): BaseMessage {
            messageId++
            return when (type) {
                "text" -> TextMessage("$messageId", from, chat, isIncoming, date, payload as String)
                else -> ImageMessage("$messageId", from, chat, isIncoming, date, payload as String)
            }
        }
    }
}

class TextMessage(id: String, from: User?, chat: Chat, isIncoming: Boolean, date: Date, var text: String?) :
    BaseMessage(id, from, chat, isIncoming, date) {
    override fun formatMessage(): String {
        return "$id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"} сообщение"
    }
}

class ImageMessage(id: String, from: User?, chat: Chat, isIncoming: Boolean, date: Date, var image: String) :
    BaseMessage(id, from, chat, isIncoming, date) {
    override fun formatMessage(): String {
        return "$id ${from?.firstName} ${if (isIncoming) "получил" else "отправил"} изображение"
    }
}