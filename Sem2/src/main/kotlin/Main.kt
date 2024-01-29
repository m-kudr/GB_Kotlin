fun main(args: Array<String>) {
    /*
За основу берём код решения домашнего задания из предыдущего семинара и дорабатываем его.
— Создайте иерархию sealed классов, которые представляют собой команды. В корне иерархии интерфейс Command.
— В каждом классе иерархии должна быть функция isValid(): Boolean, которая возвращает true, если команда введена с корректными аргументами. Проверку телефона и email нужно перенести в эту функцию.
— Напишите функцию readCommand(): Command, которая читает команду из текстового ввода, распознаёт её и возвращает один из классов-наследников Command, соответствующий введённой команде.
— Создайте data класс Person, который представляет собой запись о человеке. Этот класс должен содержать поля:
name – имя человека
phone – номер телефона
email – адрес электронной почты
— Добавьте новую команду show, которая выводит последнее значение, введённой с помощью команды add. Для этого значение должно быть сохранено в переменную типа Person. Если на момент выполнения команды show не было ничего введено, нужно вывести на экран сообщение “Not initialized”.
— Функция main должна выглядеть следующем образом. Для каждой команды от пользователя:
Читаем команду с помощью функции readCommand
Выводим на экран получившийся экземпляр Command
Если isValid для команды возвращает false, выводим help. Если true, обрабатываем команду внутри when.
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

    data class People(val name: String, var tel: String = "", var email: String = "")
    
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

    fun addPhone(name: String, tel: String, list: ArrayList<People>) {
        val i: Int = findPeople(name, list)
        if (i < 0)
            list.add(People(name, tel = tel))
        else
            list[i].tel = tel
        showPeople("", list)
    }

    fun addEmail(name: String, email: String, list: ArrayList<People>) {
        val i: Int = findPeople(name, list)
        if (i < 0)
            list.add(People(name, email = email))
        else
            list[i].email = email
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
            if (parts.size >= 2) name = parts[1]
            when (parts[0].lowercase()) {
                "show" -> showPeople(name, listOfPeoples)
                "exit" -> toExit = true
                "help" -> showHelp()
                "?" -> showHelp()
                "add" -> {
                    if (parts.size == 4) {
                        //name = parts[1]
                        var data = parts[3].lowercase()
                        when (parts[2].lowercase()) {
                            "phone" -> {
                                if (data.matches(Regex("""\d+""")))
                                    addPhone(name, data, listOfPeoples)
                                else {
                                    println("УКАЗАН НЕВЕРНЫЙ phone!")
                                    continue
                                }
                            }

                            "email" -> {
                                if (data.matches(Regex("""\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6}""")))
                                    addEmail(name, data, listOfPeoples)
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
                        //addPeople(name, field, data, listOfPeoples)
                    }
                }

                else -> println("НЕИЗВЕСТНАЯ КОМАНДА '${parts[0]}'! (для справки введите ? или help)")
            }
        }
    }
    println("Завершение программы \"Справочник\"")
}