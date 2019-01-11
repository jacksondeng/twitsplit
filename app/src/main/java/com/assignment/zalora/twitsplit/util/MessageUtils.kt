package com.assignment.zalora.twitsplit.util

import timber.log.Timber

class MessageUtils(){

    fun split(msg : String) : List<String>{
        var splitString : List<String>
        val list : List<String>
        if(checkIsLengthOverMax(msg)){
            splitString = concatMsg(msg.split(" "))
        }
        else{
            splitString = mutableListOf(msg)
        }

        return splitString
    }

    fun concatMsg(list : List<String>) : List<String>{
        var msg = ""
        var returnList : MutableList<String> = mutableListOf()
        for(index in list.indices){
            if(msg.length+list.get(index).length<50){
                msg += list.get(index)
                msg += " "
            }else{
                returnList.add(msg)
                msg = list.get(index)
            }
        }
        returnList.add(msg)
        return returnList
    }

    fun checkIsLengthOverMax(msg : String) : Boolean{
        if(msg.length>50){
            return true
        }
        return false
    }

    fun validateMsg(list : List<String>) : Boolean{
        for(msg in list){
            if(checkIsLengthOverMax(msg)){
                return false
            }
        }

        return true
    }
}