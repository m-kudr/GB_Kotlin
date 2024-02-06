import java.io.File
import java.io.IOException

sealed interface Command {
    class Help() : Command {

    }

    class Add(val data: String) {

    }

    class Show() {

    }
}

fun main(args: Array<String>) {
    /*
Продолжаем дорабатывать домашнее задание из предыдущего семинара.
За основу берём код решения из предыдущего домашнего задания.

— Измените класс Person так, чтобы он содержал список телефонов и список почтовых адресов,
  связанных с человеком.
— Теперь в телефонной книге могут храниться записи о нескольких людях.
  Используйте для этого наиболее подходящую структуру данных.
— Команда AddPhone теперь должна добавлять новый телефон к записи соответствующего человека.
— Команда AddEmail теперь должна добавлять новый email к записи соответствующего человека.
— Команда show должна принимать в качестве аргумента имя человека и выводить связанные с
  ним телефоны и адреса электронной почты.
— Добавьте команду find, которая принимает email или телефон и выводит список людей,
  для которых записано такое значение.
    */

    println("Программа \"Справочник\"")


    fun showHelp() {
        println(
            """СПРАВКА:
    add <Имя> phone <Номер_телефона>
    add <Имя> email <Адрес_электронной почты>
    show - вывод всего списка контактов
    show <Имя> - вывод контакта по имени
    find <Строка_поиска> - вывод контактов, данные которых содержат строку поиска
    export <Имя_файла> - экспорт контактов в текстовый файл в формате JSON
    exit - выход из программы
    help или ? - вывод этой справки
ПРИМЕР КОМАНД:
    add Tom email tom@example.com
    add Tom phone 7876743558
    show Tom
    export /Users/user/file.json"""
        )
    }

    data class People(
        val name: String,
        var telList: MutableList<String> = mutableListOf(),
        var emailList: MutableList<String> = mutableListOf()
    )

    //создаём список контактов для примера
    val listOfPeoples: MutableList<People> = mutableListOf()
    listOfPeoples.add(People("Alice", mutableListOf("123456", "2222"), mutableListOf("alice@mail.com")))
    listOfPeoples.add(People("Bob", telList = mutableListOf("654321")))
    listOfPeoples.add(People("Tom", emailList = mutableListOf("tom@example.com", "tom2@example2.com")))

    fun findPeople(name: String, list: List<People>): Int {
        for (i in 0 until list.size)
            if (list[i].name.lowercase() == name.lowercase()) return i
        return -1
    }

    fun showPeople(name: String, list: List<People>) {
        if (list.size == 0)
            println("СПРАВОЧНИК ПУСТ! СНАЧАЛА ДОБАВЬТЕ КОНТАКТЫ")
        else {
            if (name == "")
                for (i in list.indices) println("${i + 1}: ${list[i].name} (phone: ${list[i].telList}, e-mail: ${list[i].emailList})")
            else {
                val i = findPeople(name, list)
                if (i >= 0)
                    println("${list[i].name} (phone: ${list[i].telList}, e-mail: ${list[i].emailList})")
                else
                    println("КОНТАКТ С ИМЕНЕМ \"$name\" НЕ НАЙДЕН! ВВЕДИТЕ ДРУГОЕ ИМЯ.")
            }
        }
    }

    fun addPhone(name: String, tel: String, list: MutableList<People>) {
        val i: Int = findPeople(name, list)
        if (i < 0)
            list.add(People(name, telList = mutableListOf(tel)))
        else {
            list[i].telList.remove("")
            if (!list[i].telList.contains(tel))
                list[i].telList.add(tel)
            else
                println("ТЕЛЕФОН '$tel' УЖЕ ЕСТЬ У КОНТАКТА '${list[i].name}'")
        }
        showPeople(name, list)
    }

    fun addEmail(name: String, email: String, list: MutableList<People>) {
        val i: Int = findPeople(name, list)
        if (i < 0)
            list.add(People(name, emailList = mutableListOf(email)))
        else {
            list[i].emailList.remove("")
            if (!list[i].emailList.contains(email))
                list[i].emailList.add(email)
            else
                println("ЭЛЕКТРОННАЯ ПОЧТА '$email' УЖЕ ЕСТЬ У КОНТАКТА '${list[i].name}'")
        }
        showPeople(name, list)
    }

    fun readCommand(): String {
        print("Введите команду: ")
        val input: String? = readlnOrNull()
        return if (!input.isNullOrBlank())
            input.trim()
        else ""
    }

    fun removeDoubleSpaces(str: String): String {
        var res: String = ""
        for (i in str.indices)
            if (str[i] == ' ' && i + 1 < str.length && str[i + 1] == ' ')
                continue
            else
                res += str[i]
        return res
    }

    fun find(findString: String, list: List<People>) {
        if (findString == "")
            println("НЕ ЗАДАН ПАРАМЕТР ДЛЯ ПОИСКА! (для справки введите ? или help)")
        else
            if (list.isNotEmpty()) {
                var count: Int = 0
                for (item in list) {
                    if (item.telList.contains(findString)) {
                        showPeople(item.name, list)
                        count += 1
                    }
                    if (item.emailList.contains(findString)) {
                        showPeople(item.name, list)
                        count += 1
                    }
                }
                if (count > 0)
                    println("НАЙДЕНО КОНТАКТОВ: $count")
                else
                    println("КОНТАКТОВ С ДАННЫМИ '$findString' НЕ НАЙДЕНО")
            } else
                println("СПРАВОЧНИК ПУСТ! СНАЧАЛА ДОБАВЬТЕ КОНТАКТЫ")
    }

    fun export(pathFile: String, list: List<People>) {
        if (list.isNullOrEmpty())
            println("ЭКСПОРТ В ФАЙЛ НЕ ВЫПОЛНЕН, Т.К. СПРАВОЧНИК ПУСТ! СНАЧАЛА ДОБАВЬТЕ КОНТАКТЫ")
        else {
            //File(pathFile).writeText("Text to write")
            var text: String = "[\n"
            val form = { a: String -> "\"$a\"," }
            for (i in list) {
                text += "\t{\n"
                text += "\t\t\"name\": \"${i.name}\",\n"
                text += "\t\t\"phones\": ["
                for (tel in i.telList) text += form(tel)
                if (i.telList.size > 0) text = text.dropLast(1)
                text += "],\n\t\t\"emails\": ["
                for (mail in i.emailList) text += form(mail)
                if (i.emailList.size > 0) text = text.dropLast(1)
                text += "]\n\t},\n"
            }
            text = text.dropLast(2)
            text += "\n]"
            try {
                File(pathFile).writeText(text)
                println("ЭКСПОРТ КОНТАКТОВ В ФАЙЛ '$pathFile' УСПЕШНО ВЫПОЛНЕН.")

            } catch (e: IOException) {
                println("ЭКСПОРТ НЕ ВЫПОЛНЕН ИЗ-ЗА ОШИБКИ: " + e.message)
            }
        }
    }

    var toExit: Boolean = false
    while (!toExit) {
        val command: String = removeDoubleSpaces(readCommand())
        if (command != "") {
            val parts = command.split(' ')
            var param: String = ""
            if (parts.size > 1)
                param = parts[1]
            when (parts[0].lowercase()) {
                "export" -> if (param != "") export(
                    param,
                    listOfPeoples
                ) else println("В КОМАНДЕ НЕ УКАЗАНО ИМЯ ФАЙЛА ДЛЯ ЭКСПОРТА")

                "show" -> showPeople(param, listOfPeoples)
                "exit" -> toExit = true
                "help", "h", "?" -> showHelp()
                "find" -> find(param, listOfPeoples)
                "add" -> {
                    if (parts.size == 4) {
                        var data = parts[3].lowercase()
                        when (parts[2].lowercase()) {
                            "phone" -> {
                                if (data.matches(Regex("""\d+""")))
                                    addPhone(param, data, listOfPeoples)
                                else {
                                    println("УКАЗАН НЕВЕРНЫЙ phone!")
                                    continue
                                }
                            }

                            "email" -> {
                                if (data.matches(Regex("""\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6}""")))
                                    addEmail(param, data, listOfPeoples)
                                else {
                                    println("УКАЗАН НЕВЕРНЫЙ email!")
                                    continue
                                }
                            }

                            else -> {
                                println("НЕИЗВЕСТНЫЙ ПАРАМЕТР '${parts[2]}' КОМАНДЫ 'add'! (для справки введите ? или help)")
                                continue
                            }
                        }
                    } else println("НЕВЕРНЫЕ ПАРАМЕТРЫ КОМАНДЫ 'add' (для справки введите ? или help)")
                }

                else -> println("НЕИЗВЕСТНАЯ КОМАНДА '${parts[0]}' (для справки введите ? или help)")
            }
        }
        println()
    }
    println("Завершение программы \"Справочник\"")
}