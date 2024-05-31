package com.example.chatbot

import android.os.Message
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatBotVM : ViewModel(){
    val list by lazy {
        mutableStateListOf<ChatData>()
    }
    private val genAI by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = "AIzaSyDSilHoLeqpD2_E7uT10hwX8QZZfqNaZq0"
        )
    }
    fun sendMessage(message: String) = viewModelScope.launch {
        val chat:Chat = genAI.startChat()
        list.add(ChatData(message,ChatRoleEnum.USER.role))
        chat.sendMessage(
            content(ChatRoleEnum.USER.role) { text(message) }
        ).text?.let{
            list.add(ChatData(it, ChatRoleEnum.MODEL.role))
        }

    }
}