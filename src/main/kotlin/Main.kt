import ftp.ClassicFtpClient
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    useClassicFtp()
}

fun useClassicFtp() {
    val current = LocalDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
    val formatted = current.format(formatter)

    println("Current Date and Time is: $formatted")

    val client = ClassicFtpClient("localhost", port = 21, "test", "test")
    println("> open")
    client.open()
    println("> listFiles")
    client.listFiles("/").forEach { file -> println(file) }
    println("> upload data")
    client.uploadFile("/data-${formatted}.txt", "Hello, ${formatted}".byteInputStream())
    println("> close")
    client.close()
}
