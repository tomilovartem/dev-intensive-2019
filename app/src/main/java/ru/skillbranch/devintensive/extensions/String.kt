package ru.skillbranch.devintensive.extensions


fun String.truncate(index:Int = 16, endStr: String = "..."):String{
    val trimmedStr = this.trim()
    return if(trimmedStr.length <= index + 1) trimmedStr
    else{
        trimmedStr.substring(0 .. index).trim() + endStr
    }
}

fun String.stripHtml():String{
    return this.replace(Regex("(<.*?>)|(&[a-zA-Z0-9#]+;)"),"").replace(Regex(" +")," ")
}
