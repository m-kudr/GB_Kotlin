import java.util.*
import kotlin.collections.ArrayList

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
    fun showHelp() {
        println(
            """СПРАВКА:
    add <Имя> phone <Номер телефона>
    add <Имя> email <Адрес электронной почты>
    show - вывод всего списка контактов
    show <Имя> - вывод контакта по имени
    exit - выход из программы
    help или ? - вывод этой справки
ПРИМЕР КОМАНД:
    add Tom email tom@example.com
    add Tom phone +7876743558"""
        )
    }

    data class People(val name: String, var tel: String = "", var email: String = "") {

    }
    //создаём список контактов
    val listOfPeoples: ArrayList<People> = ArrayList()
    listOfPeoples.add(People("Alice", "123456", "alice@mail.com"))
    listOfPeoples.add(People("Bob", "654321", "bob@mail.com"))
    listOfPeoples.add(People("Tom", "", "tom@example.com"))

    fun showPeople(name: String, list: ArrayList<People>) {
        if (list.size == 0)
            println("СПРАВОЧНИК ПУСТ!")
        else {
            var count: Int = 0
            for (i in list)
                if (i.name.lowercase() == name.lowercase() || name == "") {
                    count++
                    println("$count: ${i.name} (phone: ${i.tel}, e-mail: ${i.email})")
                }
        }
    }

    fun findPeople(name: String, list: ArrayList<People>): Int {
        for (i in 0 until list.size)
            if (list[i].name.lowercase() == name.lowercase()) return i
        return -1
    }

    fun addPeople(name: String, field: Int, data: String, list: ArrayList<People>) {
        val findIndex: Int = findPeople(name, list)
        if (findIndex < 0) {
            when (field) {
                1 -> list.add(People(name, tel = data))
                2 -> list.add(People(name, email = data))
            }
        } else when (field) {
            1 -> list[findIndex].tel = data
            2 -> list[findIndex].email = data
        }
        showPeople("", list)
    }

    var toExit: Boolean = false
    println("Программа \"Справочник\"")
    while (!toExit) {
        print("Введите команду: ")
        val input = readlnOrNull()
        if (input != "" && input != null) {
            val inputText = input.trim()
            val parts = inputText.split(' ')
            var name: String = ""
            var data: String = ""
            var field: Int = 0
            if (parts.size == 2) name = parts[1]
            when (parts[0].lowercase()) {
                "exit" -> toExit = true
                "help" -> showHelp()
                "?" -> showHelp()
                "add" -> {
                    if (parts.size == 4) {
                        name = parts[1]
                        data = parts[3].lowercase()
                        when (parts[2].lowercase()) {
                            "phone" -> {
                                if (data.matches(Regex("""[0-9]+""")))
                                    field = 1
                                else {
                                    println("УКАЗАН НЕВЕРНЫЙ phone!")
                                    continue
                                }
                            }

                            "email" -> {
                                if (!data.matches(Regex("""\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6}"""))) {
                                    println("УКАЗАН НЕВЕРНЫЙ email!")
                                    continue
                                }
                                field = 2
                            }

                            else -> {
                                println("НЕИЗВЕСТНЫЙ ПАРАМЕТР '${parts[2]}' КОМАНДЫ 'add'! (для справки введите ? или help)")
                                continue
                            }
                        }
                        addPeople(name, field, data, listOfPeoples)
                    }
                }

                "show" -> showPeople(name, listOfPeoples)
                else -> println("НЕИЗВЕСТНАЯ КОМАНДА '${parts[0]}'! (для справки введите ? или help)")
            }
        }
    }
    println("Завершение программы \"Справочник\"")
}