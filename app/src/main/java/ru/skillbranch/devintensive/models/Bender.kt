package ru.skillbranch.devintensive.models


class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {


    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }


    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (question.validateAnswer(answer)) {
            if (question.answers.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                "Отлично - ты справился\n${question.question}" to status.color
            } else {
                if(question == Question.IDLE) return question.question to status.color
                status = status.nextStatus()
                if (status == Status.NORMAL) {
                    question = Question.NAME
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                } else {
                    "Это неправильный ответ\n${question.question}" to status.color
                }
            }
        } else {
            "${question.validateStr}\n${question.question}" to status.color


        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>, val validateStr: String) {
        NAME("Как меня зовут?", listOf("бендер", "bender"), "Имя должно начинаться с заглавной буквы") {
            override fun nextQuestion(): Question = PROFESSION
            override fun validateAnswer(answer: String): Boolean {
                return !(answer.isEmpty() || answer[0].isLowerCase())
            }
        },
        PROFESSION(
            "Назови мою профессию?",
            listOf("сгибальщик", "bender"),
            "Профессия должна начинаться со строчной буквы"
        ) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validateAnswer(answer: String): Boolean {
                return !(answer.isEmpty() || answer[0].isUpperCase())
            }
        },
        MATERIAL(
            "Из чего я сделан?",
            listOf("металл", "дерево", "metal", "iron", "wood"),
            "Материал не должен содержать цифр"
        ) {
            override fun nextQuestion(): Question = BDAY
            override fun validateAnswer(answer: String): Boolean {
                return !answer.contains(Regex("[1-9]+"))
            }
        },
        BDAY("Когда меня создали?", listOf("2993"), "Год моего рождения должен содержать только цифры") {
            override fun nextQuestion(): Question = SERIAL
            override fun validateAnswer(answer: String): Boolean {
                return !answer.contains(Regex("[^0-9]"))
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057"), "Серийный номер содержит только цифры, и их 7") {
            override fun nextQuestion(): Question = IDLE
            override fun validateAnswer(answer: String): Boolean {
                return !(answer.length != 7 || answer.contains(Regex("[^0-9]")))
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf(), "") {
            override fun nextQuestion(): Question = IDLE
            override fun validateAnswer(answer: String): Boolean = true
        };

        abstract fun nextQuestion(): Question
        abstract fun validateAnswer(answer: String): Boolean
    }
}