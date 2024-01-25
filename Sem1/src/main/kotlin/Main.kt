import java.util.*

fun main(vararg args: String) {
    /*
    //-------------------------------------------------
    fun stars(a: Int, b: Int, c: Int) {
        var count = 0
        for (line in 0..b * 2) {
            //print(line)
            for (i in 1..a) print("*")
            for (j in 1..count) print("*")
            if (line < b) count += c else count -= c
            println()
        }
    }
    stars(5, 2, 2)
    //stars(1, 3, 2)
    //stars(1, 2, 4)
    */

    /*
    //-------------------------------------------------
    fun sumAll(vararg numbers: Int) = numbers.sum()

    println("sumAll = ${sumAll(1, 5, 20)}")
    println("sumAll = ${sumAll()}")
    println("sumAll = ${sumAll(2, 3, 4, 5, 6, 7)}")
    println("---------------------------")

    fun createOutputString(name: String, age: Int = 42, isStudent: Boolean? = false) =
        if (isStudent == true) "student $name has age of $age" else "$name has age of $age"

    println(createOutputString("Alice"))
    println(createOutputString("Bob", 23))
    println(createOutputString(isStudent = true, name = "Carol", age = 19))
    println(createOutputString("Daniel", 32, isStudent = null))
    println("---------------------------")

    fun multiplyBy(a: Int?, b: Int?) = if (a != null && b != null) a * b else null

    println(multiplyBy(null, 4))
    println(multiplyBy(3, 4))

    fun sum(a: Int, b: Int) = a + b
    if (args.size >= 2) {
        val a = args[0].toIntOrNull()
        val b = args[1].toIntOrNull()
        if (a != null && b != null)
            println("$a + $b = " + sum(a, b))
    } else
        println("Error: few arguments")
    */
    //println("Program arguments: ${args.joinToString()}")

    /*
    //-------------------------------------------------
    Написать программу, которая обрабатывает введённые пользователем в консоль команды:
    exit
    help
    add <Имя> phone <Номер телефона>
    add <Имя> email <Адрес электронной почты>

    После выполнения команды, кроме команды exit, программа ждёт следующую команду.

    Имя – любое слово.
    Если введена команда с номером телефона, нужно проверить, что указанный телефон может начинаться с +, затем идут только цифры. При соответствии введённого номера этому условию – выводим его на экран вместе с именем, используя строковый шаблон. В противном случае - выводим сообщение об ошибке.
    Для команды с электронной почтой делаем то же самое, но с другим шаблоном – для простоты, адрес должен содержать три последовательности букв, разделённых символами @ и точкой.

    Пример команд:
    add Tom email tom@example.com
    add Tom phone +7876743558
    */
    // "456789".matches(Regex("""[0-9]+""")) // true
    fun printHelp() {
        println("СПРАВКА:\nadd <Имя> phone <Номер телефона>\nadd <Имя> email <Адрес электронной почты>/n")
        println("exit - выход из программы")
        println("help - вывод этой справки")
        println("Пример команд:\nadd Tom email tom@example.com\nadd Tom phone +7876743558")
    }

    var toExit: Boolean = false
    while (!toExit) {
        print("Введите команду: ")
        val input = readlnOrNull()
        //println(input)
        if (input != "")
            when {
                input?.lowercase(Locale.getDefault()) == "exit" -> toExit = true
                input?.lowercase(Locale.getDefault()) == "help" -> printHelp()
                else -> {
                    println("НЕИЗВЕСТНАЯ КОМАНДА!")
                    printHelp()
                }
            }
    }
}