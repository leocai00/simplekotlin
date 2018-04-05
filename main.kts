// Explore a simple class

println("UW Homework: Simple Kotlin")

// write a "whenFn" that takes an arg of type "Any" and returns a String

// write an "add" function that takes two Ints, returns an Int, and adds the values
// write a "sub" function that takes two Ints, returns an Int, and subtracts the values
// write a "mathOp" function that takes two Ints and a function (that takes two Ints and returns an Int), returns an Int, and applies the passed-in-function to the arguments

// write a class "Person" with first name, last name and age

// write a class "Money"

fun whenFn(arg: Any): String {
    return when (arg) {
        "Hello" -> "World"
        is String -> "I don't understand"
        0 -> "zero"
        1 -> "one"
        2..10 -> "low number"
        is Number -> "a number"
        else -> "I don't understand"
    }
}

fun add(num1: Int, num2: Int): Int {
    return num1 + num2
}

fun sub(num1: Int, num2: Int): Int {
    return num1 - num2
}

fun mathOp(num1: Int, num2: Int, function:(num3: Int, num4: Int) -> Int): Int {
    return function(num1,num2)
}

class Person(var firstName: String, var lastName: String, var age: Int) {
    val debugString = "[Person firstName:$firstName lastName:$lastName age:$age]"

    fun equals(other: Person): Boolean {
        return this.firstName == other.firstName && this.lastName == other.lastName && this.age == other.age
    }

    override fun hashCode(): Int {
        return this.firstName.hashCode() + this.lastName.hashCode() + this.age.hashCode()
    }
}

class Money(var amount: Int, var currency: String) {
    init {
        if(!this.currency.equals("USD") && !this.currency.equals("EUR") && !this.currency.equals("CAN") && !this.currency.equals("GBP")) {
            throw IllegalArgumentException("Need to be USD, EUR, CAN or GBP")
        }
        if(this.amount < 0) {
            throw IllegalArgumentException("Invalid input amount")
        }
    }

    fun convert(convertTo: String): Money {
        var converted: Double
        when(this.currency) {
            "EUR" -> converted = this.amount * 1.22
            "CAN" -> converted = this.amount * 0.78
            "GBP" -> converted = this.amount * 1.4
            else -> converted = this.amount * 1.0
        }
        when(convertTo) {
            "EUR" -> converted = converted * 0.82
            "CAN" -> converted = converted * 1.28
            "GBP" -> converted = converted * 0.71
            else -> converted = converted * 1.0
        }
        return Money(converted.toInt(), convertTo)
    }

    operator fun plus(add: Money): Money {
        return Money(this.amount + add.convert(add.currency).amount, this.currency)
    }
}


// ============ DO NOT EDIT BELOW THIS LINE =============

print("When tests: ")
val when_tests = listOf(
    "Hello" to "world",
    "Howdy" to "Say what?",
    "Bonjour" to "Say what?",
    0 to "zero",
    1 to "one",
    5 to "low number",
    9 to "low number",
    17.0 to "I don't understand"
)
for ((k,v) in when_tests) {
    print(if (whenFn(k) == v) "." else "!")
}
println("")

print("Add tests: ")
val add_tests = listOf(
    Pair(0, 0) to 0,
    Pair(1, 2) to 3,
    Pair(-2, 2) to 0,
    Pair(123, 456) to 579
)
for ( (k,v) in add_tests) {
    print(if (add(k.first, k.second) == v) "." else "!")
}
println("")

print("Sub tests: ")
val sub_tests = listOf(
    Pair(0, 0) to 0,
    Pair(2, 1) to 1,
    Pair(-2, 2) to -4,
    Pair(456, 123) to 333
)
for ( (k,v) in sub_tests) {
    print(if (sub(k.first, k.second) == v) "." else "!")
}
println("")

print("Op tests: ")
print(if (mathOp(2, 2, { l,r -> l+r} ) == 4) "." else "!")
print(if (mathOp(2, 2, ::add ) == 4) "." else "!")
print(if (mathOp(2, 2, ::sub ) == 0) "." else "!")
print(if (mathOp(2, 2, { l,r -> l*r} ) == 4) "." else "!")
println("")


print("Person tests: ")
val p1 = Person("Ted", "Neward", 47)
print(if (p1.firstName == "Ted") "." else "!")
p1.age = 48
print(if (p1.debugString == "[Person firstName:Ted lastName:Neward age:48]") "." else "!")
println("")

print("Money tests: ")
val tenUSD = Money(10, "USD")
val twelveUSD = Money(12, "USD")
val fiveGBP = Money(5, "GBP")
val fifteenEUR = Money(15, "EUR")
val fifteenCAN = Money(15, "CAN")
val convert_tests = listOf(
    Pair(tenUSD, tenUSD),
    Pair(tenUSD, fiveGBP),
    Pair(tenUSD, fifteenEUR),
    Pair(twelveUSD, fifteenCAN),
    Pair(fiveGBP, tenUSD),
    Pair(fiveGBP, fifteenEUR)
)
for ( (from,to) in convert_tests) {
    print(if (from.convert(to.currency).amount == to.amount) "." else "!")
}
val moneyadd_tests = listOf(
    Pair(tenUSD, tenUSD) to Money(20, "USD"),
    Pair(tenUSD, fiveGBP) to Money(20, "USD"),
    Pair(fiveGBP, tenUSD) to Money(10, "GBP")
)
for ( (pair, result) in moneyadd_tests) {
    print(if ((pair.first + pair.second).amount == result.amount &&
              (pair.first + pair.second).currency == result.currency) "." else "!")
}
println("")
