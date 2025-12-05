fun main(){
    showText()
    println(greeting("Kotlin", 5))
    println(greeting(name="David", 54))
}

fun showText() {
    println("Hello, Kotlin")
}

fun greeting(name: String, age: Int): String {
    return "You're $name. You're now $age years old"
}