package com.assignment.zalora.twitsplit.util

import timber.log.Timber

class MessageUtils(){
    companion object {
        const val MAX_MESSAGE_LENGTH = 50
    }


    fun split(msg : String) : List<String>{
        var splitString : List<String> = ArrayList()
        var trimmedMsg = trimSpacesAndNewlines(msg)
        var numOfSubStr = calculateMaxNumSubStr(trimmedMsg)

        if(checkIsLengthOverMax(trimmedMsg)){
            if(!validateMsg(trimmedMsg.split(" "))){
                return splitString
            }
            splitString = concatMsg(trimmedMsg.split(" "),numOfSubStr)

            if(!validateMsg(splitString) || numOfSubStr < splitString.size){
                numOfSubStr++
                splitString = concatMsg(trimmedMsg.split(" "),numOfSubStr)
            }
        }
        else{
            splitString = mutableListOf(trimmedMsg)
        }

        return splitString
    }

    fun concatMsg(list : List<String>,numOfSubStr : Int) : List<String>{
        var msg = ""
        var returnList : MutableList<String> = mutableListOf()
        var subStrCount = 1
        for(index in list.indices){
            if(msg.length == 0){
                msg += (subStrCount).toString()+"/"+(numOfSubStr)
            }

            if(msg.length+list.get(index).length<MAX_MESSAGE_LENGTH){
                msg += " " + list.get(index)
            }else{
                returnList.add(msg)
                subStrCount++
                msg = (subStrCount).toString()+"/"+(numOfSubStr)+ " " + list.get(index)
            }
        }

        // Add last msg into returnList
        returnList.add(msg)
        return returnList
    }

    fun checkIsLengthOverMax(msg : String) : Boolean{
        if(msg.length>MAX_MESSAGE_LENGTH){
            return true
        }
        return false
    }

    fun calculateMaxNumSubStr(msg : String) : Int{
        var maxSubStr = msg.length.div(MAX_MESSAGE_LENGTH.toDouble())
        return Math.ceil(maxSubStr).toInt()
    }

    fun validateMsg(list : List<String>) : Boolean{
        for(msg in list){
            if(checkIsLengthOverMax(msg)){
                return false
            }
        }
        return true
    }

    fun trimSpacesAndNewlines(msg : String) : String{
        // Replace duplicate whitespaces and newline
        var msgTrimmed =  msg.trim().replace(" +".toRegex(), " ").replace("\n+".toRegex(), "\n")
        Timber.d("Newline  "+ msgTrimmed)
        return msgTrimmed
    }

}