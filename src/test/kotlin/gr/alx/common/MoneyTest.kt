package gr.alx.common

import gr.alx.common.domain.model.Money
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MoneyTest {
    @Test
    fun `should add integer money`() {
        val money = Money(10)
        val money2 = Money(20)

        val result = money + money2

        assertThat(result).isEqualTo(Money(30.00))
    }

    @Test
    fun `should add money with one decimal`() {
        val money = Money(10.5)
        val money2 = Money(20.2)

        val result = money + money2

        assertThat(result).isEqualTo(Money(30.70))
    }

    @Test
    fun `should add money with two decimals`() {
        val money = Money(10.53)
        val money2 = Money(20.22)
        val result = money + money2

        assertThat(result).isEqualTo(Money(30.75))
    }

    @Test
    fun `should divide integer money`() {
        val money = Money(20)
        val money2 = Money(10)
        val result = money / money2

        assertThat(result).isEqualTo(Money(2.00))
    }

    @Test
    fun `should divide  money with one decimal`() {
        val money = Money(20.5)
        val money2 = Money(10.5)
        val result = money / money2

        assertThat(result).isEqualTo(Money(1.95))
    }

    @Test
    fun `should divide  money with two decimals`() {
        val money = Money(20.25)
        val money2 = Money(10.05)
        val result = money / money2

        assertThat(result).isEqualTo(Money(2.01))
    }
}
