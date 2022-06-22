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

    /**
     * Upload one file.
     */
    fun uploadFile(path: String, input: InputStream)
}
