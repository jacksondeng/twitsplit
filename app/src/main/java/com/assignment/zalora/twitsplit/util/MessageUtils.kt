package com.assignment.zalora.twitsplit.util

import timber.log.Timber

class MessageUtils(){

    fun split(msg : String) : List<String>{
        var splitString : List<String>
        val list : List<String>
        if(checkIsLengthOverMax(msg)){
            list = msg.split(" ")
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
            concatStr(msg,list.get(index),returnList)
        }
        return returnList
    }

    fun concatStr(msg : String , msg1 : String,list : MutableList<String>){
        var result = ""
        if(msg.length+msg1.length<50){
            result = msg+msg1
            result += " "
        }else{
            list.add(msg)
            result = msg1
        }
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