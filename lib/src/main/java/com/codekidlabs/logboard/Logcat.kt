package com.codekidlabs.logboard

import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @class Logcat - responsible for "All Things, logcat!"
 */
class Logcat {


    // Commands
    val COMMAND_LOGCAT: String = "logcat -d"
    val NEW_LINE: String = "\n"

    private var logBuilder: StringBuilder? = null


    fun getLogcat(): String {
        logBuilder = StringBuilder()
        logBuilder!!.appendBlank()

        getLog()

        return logBuilder.toString()

    }

//    private fun getDeviceInfo() : String {
//
//    }

    private fun getLog() {
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
    fun StringBuilder.appendBlank() = this.append(NEW_LINE + NEW_LINE + NEW_LINE)

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
