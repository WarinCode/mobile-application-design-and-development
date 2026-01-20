package code

fun main(){
    println("Hello World!")

    val names = listOf<String>("John", "Jane", "Jack", "Jo", "Jim")
    println("Size of array is ${names.size}")
    for (name in names) {
        greeting(name)
    }
}

fun greeting(name: String): Unit {
    println("Hello $name")
}