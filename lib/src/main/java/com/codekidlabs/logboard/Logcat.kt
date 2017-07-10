package com.codekidlabs.logboard

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @class Logcat - responsible for "All Things, logcat!"
 */
class Logcat {


    // Commands
    val COMMAND_LOGCAT: String = "logcat -d"
    val COMMAND_DEVICE_SDK: String = "getprop ro.build.version.release"
    val COMMAND_DEVICE: String = "getprop ro.product.device"
    val COMMAND_DEVICE_BOARD: String = "getprop ro.product.board"
    val NEW_LINE: String = "\n"

    private var logBuilder: StringBuilder? = null

    private val description : String = ""


    fun getLogcat(): String {
        logBuilder = StringBuilder()

        injectDescription()
        getDeviceInfo()
        getLog()

        return logBuilder.toString()

    }

    private fun injectDescription() {
        logBuilder!!.appendLine("Problem statement from user: ")
        logBuilder!!.appendLine(description)
        logBuilder!!.appendBlank()
    }

    private fun getDeviceInfo() {
        logBuilder!!.appendLine("Device Information: ")
        processOutput(createProcess(COMMAND_DEVICE_SDK))
        processOutput(createProcess(COMMAND_DEVICE))
        processOutput(createProcess(COMMAND_DEVICE_BOARD))
        logBuilder!!.appendBlank()
    }

    private fun getLog() {
        logBuilder!!.appendLine("Logcat: ")
        return processOutput(createProcess(COMMAND_LOGCAT))
    }


    private fun createProcess(command: String): Process {
        return Runtime.getRuntime().exec(command)
    }

    private fun processOutput(process: Process) {
        val reader: BufferedReader = BufferedReader(InputStreamReader(process.inputStream))

        for (line in reader.output) {
            logBuilder!!.appendLine(line)
        }
    }

    /*
    * @extensions
    * */

    fun StringBuilder.appendLine(log: String) = this.append(log + NEW_LINE)
    fun StringBuilder.appendBlank() = this.append(NEW_LINE + NEW_LINE)

    val BufferedReader.output: Iterator<String>
        get() = object : Iterator<String> {
            override fun hasNext() = line != null

            var line = this@output.readLine()
            override fun next(): String {
                if (line == null)
                    throw NoSuchElementException()
                val result = line!!
                line = this@output.readLine()
                return result
            }
        }
}
