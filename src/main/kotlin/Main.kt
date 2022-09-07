import ftp.ClassicFtpClient
import ftp.FtpClient
import ftp.FtpsClient
import ftp.SFtpClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    if ("ftp".equals(args[0]))
        useClassicFtp()
    else if ("sftp".equals(args[0]))
        useSFtp()
    else if ("ftps".equals(args[0]))
        useFtps()
}

fun useClassicFtp() {
    doFtp(ClassicFtpClient("localhost", "test", "test"))
}

fun useSFtp() {
    doFtp(SFtpClient("localhost", "test", "test"))
}

fun useFtps() {
    doFtp(FtpsClient("localhost", "test", "test"))
}

fun doFtp(client: FtpClient) {
    val current = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss.SSS")
    val formatted = current.format(formatter)

    println("Current Date and Time is: $formatted")

    println("> open")
    client.open()
    client.passive()
    val pwd = client.workingDirectory()
    println("> pwd: $pwd")
    println("> upload data")
    client.uploadStringAsFile("data-${formatted}.txt", "Hello, ${formatted}")
    println("> listFiles")
    client.listFiles(pwd).forEach { file -> println(file) }
    println("> close")
    client.close()
}
