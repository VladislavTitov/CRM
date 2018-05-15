package com.example.vlados.crm.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Message
import com.example.vlados.crm.network.ApiMethods
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class MessagesPresenter(val navigator: Navigator?) : MvpPresenter<ItemInterface<Message>>() {
    
    var messagesMap = mutableMapOf<Long?, List<Message>>()
    var dialogMessages: List<Message> = mutableListOf()
    var messages: List<Message> = mutableListOf()
    
    fun onDialogReady(userId: Long?) {
        ApiMethods.get.getAllFromMessages(userId).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sortMessages(it, userId)
                    sortForDialog()
                    viewState.setItems(dialogMessages)
                }, {
                    it.printStackTrace()
                })
        ApiMethods.get.getAllToMessages(userId).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    sortMessages(it, userId)
                    sortForDialog()
                    viewState.setItems(dialogMessages)
                }, {
                    it.printStackTrace()
                })
    }
    
    fun getMessagesWithUser(currentId: Long?, dialogId: Long?) {
        ApiMethods.get.getAllFromToMessages(currentId, dialogId).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    messages += it
                    viewState.setItems(messages)
                }, {
                    it.printStackTrace()
                })
        ApiMethods.get.getAllFromToMessages(dialogId, currentId).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    messages += it
                    viewState.setItems(messages)
                }, {
                    it.printStackTrace()
                })
    }
    
    fun save(message: Message) {
        ApiMethods.post.postMessage(message).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    messages += it
                    viewState.setItems(messages)
                }, {
                    it.printStackTrace()
                })
    }
    
    
    private fun sortForDialog() {
        dialogMessages = mutableListOf()
        for (entry in messagesMap) {
            val list = entry.value.sortedBy { message -> message.id }
            dialogMessages += list[list.size - 1]
        }
    }
    
    private fun sortMessages(messages: List<Message>, userId: Long?) {
        
        var currentId: Long?
        for (message in messages) {
            
            currentId = when (userId) {
                message.sender?.id -> message.receiver?.id
                else -> message.sender?.id
            }
            
            
            if (messagesMap.get(currentId) == null) {
                messagesMap.put(currentId, mutableListOf())
            }
            
            var list = messagesMap.get(currentId)
            if (list != null) {
                list += list.plus(message)
                messagesMap.put(currentId, list)
            }
        }
        
    }
    
}
