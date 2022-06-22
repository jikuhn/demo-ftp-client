package ftp

import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier
import net.schmizz.sshj.xfer.InMemorySourceFile
import java.io.File
import java.io.InputStream

/**
 * https://www.baeldung.com/java-file-sftp
 *
 * SFTP uses port 22.
 */
class SFtpClient(val server: String, val user: String, val password: String) : FtpClient {
    var sftp: SSHClient

    init {
        sftp = SSHClient()
        sftp.addHostKeyVerifier(PromiscuousVerifier())
    }

    override fun open() {
        sftp.connect(server, 22)
        sftp.authPassword(user, password)
    }

    override fun close() {
        sftp.disconnect()
    }

    override fun listFiles(path: String): List<String> {
        return emptyList()
    }

    override fun uploadFile(path: String, input: InputStream) {
        val client = sftp.newSFTPClient()
        val file = object : InMemorySourceFile() {
            override fun getName(): String {
                return path
            }

            override fun getLength(): Long {
                TODO("Not yet implemented")
            }

            override fun getInputStream(): InputStream {
                return input
            }
        }
        client.put(file, path)
    }

    override fun passive() {
        // nothing
    }
}
