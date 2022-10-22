package ftp

import org.apache.commons.net.PrintCommandListener
import org.apache.commons.net.ftp.FTP
import org.apache.commons.net.ftp.FTPClient
import org.apache.commons.net.ftp.FTPFile
import org.apache.commons.net.ftp.FTPReply
import java.io.IOException
import java.io.InputStream
import java.util.*
import kotlin.streams.toList

/**
 * Old school ftp on port 21.
 * https://www.baeldung.com/java-ftp-client
 */
class ClassicFtpClient(val server: String, val user: String, val password: String) : FtpClient {
    val ftp: FTPClient = FTPClient()

    override fun open() {
        ftp.addProtocolCommandListener(PrintCommandListener(System.out))

        ftp.connect(server, 21)
        val reply = ftp.replyCode

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect()
            throw IOException("Exception in connecting to FTP Server")
        }

        if (!ftp.login(user, password))
            throw IOException("Login unsuccessful!")
        ftp.setFileType(FTP.BINARY_FILE_TYPE)
    }

    override fun protocol(): String {
        return "ftp"
    }

    override fun listFiles(path: String): List<String> {
        val files: Array<FTPFile> = ftp.listFiles(path)
        return Arrays.stream(files).map(FTPFile::getName).toList()
    }

    override fun passive() {
        ftp.enterLocalPassiveMode()
    }

    override fun uploadFile(path: String, input: InputStream, length: Long) {
        val result = ftp.storeFile(path, input)
        if (!result) {
            throw RuntimeException(ftp.replyString)
        }
    }

    override fun workingDirectory(): String {
        return ftp.printWorkingDirectory()
    }

    override fun close() {
        ftp.disconnect()
    }
}
