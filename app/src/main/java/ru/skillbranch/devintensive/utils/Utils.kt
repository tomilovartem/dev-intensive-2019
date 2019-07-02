package ru.skillbranch.devintensive.utils

import kotlin.math.abs


object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {


        val parts: List<String>? = fullName?.trim()?.ifEmpty { null }?.split(Regex("\\s+"))
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)

        return firstName to lastName

    }

    fun toInitials(firstName: String?, lastName: String?): String? {

        val initialsF = firstName?.trim()?.firstOrNull() ?: ""
        val initialsL = lastName?.trim()?.firstOrNull() ?: ""

        return "$initialsF$initialsL".ifEmpty { null }?.toUpperCase()
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val replacements = mapOf(
            'а' to "a",
            'б' to "b",
            'в' to "v",
            'г' to "g",
            'д' to "d",
            'е' to "e",
            'ё' to "e",
            'ж' to "zh",
            'з' to "z",
            'и' to "i",
            'й' to "i",
            'к' to "k",
            'л' to "l",
            'м' to "m",
            'н' to "n",
            'о' to "o",
            'п' to "p",
            'р' to "r",
            'с' to "s",
            'т' to "t",
            'у' to "u",
            'ф' to "f",
            'х' to "h",
            'ц' to "c",
            'ч' to "ch",
            'ш' to "sh",
            'щ' to "sh'",
            'ъ' to "",
            'ы' to "i",
            'ь' to "",
            'э' to "e",
            'ю' to "yu",
            'я' to "ya",
            ' ' to divider
        )

        var transliteration = ""
        payload.toCharArray().forEach {
            transliteration += replacements[it] ?: replacements[it.toLowerCase()]?.toUpperCase() ?: it
        }

        return transliteration

    }

    fun num2str(value:Int, textForms:Array<String>): String {
        val n = abs(value) % 100
        val n1 = n % 10
        return when{
            n in 11..19 -> textForms[2]
            n1 in 2..4 -> textForms[1]
            n1 == 1 -> textForms[0]
            else -> textForms[2]
        }
    }
}