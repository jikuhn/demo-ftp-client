package ftp

import org.apache.commons.net.PrintCommandListener
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPFile
import org.apache.commons.net.ftp.FTPReply
import java.io.IOException
import java.io.InputStream
import java.util.*

/**
 * Old school ftp on port 21.
 * https://www.baeldung.com/java-ftp-client
 */
class ClassicFtpClient(val server: String, val port: Int, val user: String, val password: String) {
    val ftp: FTPClient = FTPClient()

    fun open() {
        ftp.addProtocolCommandListener(PrintCommandListener(System.out))

        ftp.connect(server, port)
        val reply = ftp.replyCode

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect()
            throw IOException("Exception in connecting to FTP Server")
        }

        ftp.login(user, password)
    }

    fun listFiles(path: String): List<String> {
        ftp.enterLocalPassiveMode()
        val files: Array<FTPFile> = ftp.listFiles(path)
        return Arrays.stream(files).map(FTPFile::getName).toList()
    }

    fun uploadFile(path: String, input: InputStream) {
        ftp.storeFile(path, input)
    }

    fun close() {
        ftp.disconnect()
    }
}
