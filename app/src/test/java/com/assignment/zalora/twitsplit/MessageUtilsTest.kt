package com.assignment.zalora.twitsplit

import com.assignment.zalora.twitsplit.util.MessageUtils
import org.junit.Assert
import org.junit.Test

class MessageUtilsTest {
    private var messageUtils = MessageUtils()

    @Test
    fun trimmedMessage(){
        var msg = "Test\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nme"
        var output = "Test\nme"
        Assert.assertEquals(messageUtils.trimSpacesAndNewlines(msg),output)
    }

    @Test
    fun splitMessage_shorterThan_50() {
        Assert.assertEquals(messageUtils.split("I can't believe"), arrayListOf("I can't believe"))
    }

    @Test
    fun splitMessage_longerThan_50() {
        var msg = "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself."
        var output = arrayListOf("1/2 I can't believe Tweeter now supports chunking",
            "2/2 my messages, so I don't have to do it myself.")
        Assert.assertEquals(messageUtils.split(msg), output)
    }
    @Test
    fun splitMessage_withWordMorethan_50(){
        var msg = "Hello, WORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDDWORLDDD"
        var output = ArrayList<String>()
        Assert.assertEquals(messageUtils.split(msg), output)
    }

    @Test
    fun splitMessage_withNumOfSubStr_greaterThan_expectedNumOfSubstr(){
        var msg = "I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself 1234."
        var output = arrayListOf("1/3 I can't believe Tweeter now supports chunking",
            "2/3 my messages, so I don't have to do it myself",
            "3/3 1234.")
        Assert.assertEquals(messageUtils.split(msg), output)
    }

    @Test
    fun splitMessage_withSpacesOnly_shorterThan_50(){
        var msg = "          "
        var output = arrayListOf("")
        Assert.assertEquals(messageUtils.split(msg), output)
    }


    @Test
    fun splitMessage_withSpacesOnly_longerThan_50(){
        // 50 spaces each
        var msg = "                                                  " + "                                                  "
        var output = arrayListOf("")
        Assert.assertEquals(messageUtils.split(msg), output)
    }

    @Test
    fun splitMessage_withNewLineOnly(){
        // 50 spaces each
        var msg = "Test\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nme"
        var output = arrayListOf("Test\nme")
        Assert.assertEquals(messageUtils.split(msg), output)
    }



}