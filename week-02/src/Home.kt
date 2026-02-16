abstract class homeAppliance(val name: String) {
    abstract fun turnOn()

    fun showName(){
        println("Appliance: $name")
    }
}

class fan: homeAppliance("Electric Fan"){
    override fun turnOn() {
        println("$name is spinning")
    }
}

class refrigerator: homeAppliance("Refrigerator"){
    override fun turnOn() {
        println("$name is cooling")
    }
}

fun main(){
    val appliance: List<homeAppliance> = listOf(fan(), refrigerator(), fan())

    for (item in appliance) {
        item.showName()
        item.turnOn()
        println("-------------------")
    }
}