fun main(){
    var dirtyLevel = 20
    val waterFilter = { dirty: Int -> dirty / 2 }
    println(waterFilter(dirtyLevel))

    val sum: (Int, Int) -> Int = { x, y -> x + y }
    println(sum(5, 7))

    var greeting: String? = "Hello"
    greeting = null

    var age: Int? = null
    age = 30
}