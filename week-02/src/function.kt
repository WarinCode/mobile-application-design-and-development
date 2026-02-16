fun main(){
    println("Hello World")
    createGreeting("Alice")
    createGreeting("Charlie", "Good afternoon", '#')
    createGreeting(punc = '?', name = "Bob")
    createGreeting("David", punc = ':')
    createGreeting("Rain", greeting = "Hi", '*')
}

fun createGreeting(name: String = "Guest", greeting: String = "Hello", punc: Char  = '!'){
    println("$greeting, $name$punc")
}