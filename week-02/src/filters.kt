fun main() {
    val decorations = listOf("rock", "pagoda", "plastic plant", "alligator", "flowerpot")
    println(decorations.filter { it[0] == 'p' })
    println(decorations.filter { it[2] == 'w' })

    val num = listOf(5, 12, 8, 25, 4, 17, 30)
    println(num.filter { it > 10 })
    println(num.filter { it % 2 == 0 })
    println(decorations.filter { it.length > 10 })
}