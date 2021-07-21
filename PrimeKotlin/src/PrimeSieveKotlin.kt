import java.lang.System.currentTimeMillis
import java.util.BitSet
import java.util.concurrent.TimeUnit
import kotlin.math.sqrt

private const val LIMIT = 1000000
private const val TIME = 5

// mapping number of primes within the limit of these milestones to 1 million
// using kotlin's built in mapping function instead of importing java's mapping utility
private val MY_DICT = mapOf(10 to 1, 1000 to 168, 10000 to 1229,
    100000 to 9592, 1000000 to 78498 )

    // initializing sievesize and bitarray
    private var sieveSize: Int = 0
    private var bitArray: BitSet = BitSet(0)


    fun primeSieve(sizelimit: Int) {
        // Upper limit of numbers, highest prime we'll consider
        sieveSize = sizelimit
        // since we filter out the evens, we only need half as many bits
        val bitArrayLength = (sieveSize + 1) / 2
        bitArray = BitSet(bitArrayLength)
        bitArray.set(0, bitArrayLength, true)
    }


    fun runSieve() {
        var factor = 3

        // calculating prime numbers to the sieveSize limit(under 1000)
        while (factor < sqrt(sieveSize.toDouble()).toInt()) {
            for (num in factor..sieveSize) {
                if (getBit(num)) {
                    factor = num
                    break
                }
            }

            var num = factor * 3

            // calculating prime numbers to the sieveSize limit(over 1000)
            while (num <= sieveSize) {
                clearBit(num)
                num += factor * 2
            }

            factor += 2 // No need to check even numbers, so skip to next odd (factor = 3, 5, 7, 9...)
        }
    }

    // uses cardinality function to return number of true prime numbers
    private fun countPrimes(): Int {
        return bitArray.cardinality()
    }


    //if the count of primes matches the map then the results are valid
    private fun validateResults(): Boolean {
        return if (MY_DICT.containsKey(sieveSize) && MY_DICT[sieveSize] == countPrimes()) true
        else return false
    }

    // if number is wholly divisible by 2 then its not prime and marked false
    private fun getBit(index: Int): Boolean {
        return if (index % 2 == 0) false
        else bitArray[index / 2]
    }

    // if number is even it gets caught and marked false
    private fun clearBit(index: Int) {
        if (index % 2 == 0) {
            println("You are setting even bits, which is sub-optimal")
        }
        bitArray[index / 2] = false
    }


    fun printResults(showResults: Boolean, duration: Long, passes: Long) {
        if (showResults) println("2, ")
        var count = 1

        //prints all prime numbers that pass the check
        for (num in 3..sieveSize) {
            if (getBit(num)) {
                if (showResults) println(num.toString())
                count++
            }
        }

        if (showResults) println("")
        System.out.printf(
            "Passes: %d, Time: %f, Avg pass time: %f, Limit: %d, Count: %d, Valid: %s%n", passes,
            duration.toDouble(), duration.toDouble() / passes, sieveSize, count, validateResults()
        )
    }


fun main(){
    // initializing passes as well as the time start & PrimeSieve class
    var passes:Long = 0
    val tStart = currentTimeMillis()
    // no need to create new class objects in kotlin so I made the sieve immutable

    // run PrimeSieve functions & count passes for 5 seconds
    while (TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis() - tStart) < TIME) {
        primeSieve(LIMIT)
        runSieve()
        passes++
    }

    // displays the results if showResults is true
    val totalTime = (currentTimeMillis() - tStart)
    printResults(true, TimeUnit.MILLISECONDS.toSeconds(totalTime), passes)
}

