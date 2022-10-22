package ftp

import java.io.InputStream

interface FtpClient {
    /**
     * Open the communication link.
     */
    fun open()

    /**
     * Disconnect from the server
     */
    fun close()

    /**
     * Turn on passive mode.
     */
    fun passive();

    /**
     * Just list the files.
     */
    fun listFiles(path: String): List<String>

    fun uploadStringAsFile(path: String, content: String) {
        uploadFile(path, content.byteInputStream(), content.length.toLong())
    }
    /**
     * Upload one file.
     */
    fun uploadFile(path: String, input: InputStream, length: Long)

    fun workingDirectory(): String

    fun protocol(): String
}
