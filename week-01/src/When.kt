fun main(){
    val day = "Monday"
    when(day){
        "Sunday" -> println("Holiday")
        "Monday" -> println("Start of the week")
        "Friday" -> println("Almost weekend")
        else -> println("Just a regular day")
    }

    val score = 80
    val result = when(score) {
        in 90..100 -> "Excellent"
        in 70..89 -> "Good"
        else -> "Needs improvement"
    }
    println(result)

    when {
        score < 50 -> println("Needs improvement")
        score in 90..100 -> println("Excellent")
        score in 70..89 -> println("Good")
        else -> println("Error")
    }
}