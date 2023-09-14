package demo

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

fun main() {
    println(App().greeting)
}

fun paramInFunction(num: Int, magic: String) {
    println("Only using one parameter: $num")
}
