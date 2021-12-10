package pl.training.goodweather.commons

import junit.framework.Assert.assertEquals
import org.junit.Test
import java.lang.Exception

class CalculatorTest {

    private val sut = Calculator()

    @Test
    fun `given two numbers when add then returns their sum`() {
        // given
        val firstNumber = 1.0
        val secondNumber = 2.0
        // when
        val actual = sut.add(firstNumber, secondNumber)
        // then
        assertEquals(3.0, actual)
    }

    @Test(expected = Exception::class)
    fun `given two numbers where divisor equals zero when divide then throws exception`() {
        sut.divide(10.0, 0.0)
    }

}