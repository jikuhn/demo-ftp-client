package ftp

import org.apache.commons.net.ftp.FTPSClient
import java.io.InputStream

class FtpsClient(val server: String, val user: String, val password: String): FtpClient {
    val client = FTPSClient()

    override fun open() {
        client.connect(server, 21)
        print("reply: ${client.replyString}")
        println("login: ${client.login(user, password)}")
    }

    override fun close() {
        client.logout()
        client.disconnect()
    }

    override fun passive() {
        client.enterLocalPassiveMode()
    }

    override fun listFiles(path: String): List<String> {
        val files = client.listFiles(path)
        print("list files reply: ${client.replyString}")
        return files.map { file -> file.name }
    }

    override fun uploadFile(path: String, input: InputStream, length: Long) {
        client.storeFile(path, input)
        print("upload file reply: ${client.replyString}")
    }

    override fun workingDirectory(): String {
        return client.printWorkingDirectory()
    }

    override fun protocol(): String {
        return "ftps"
    }
}
