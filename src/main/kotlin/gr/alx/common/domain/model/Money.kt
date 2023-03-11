package gr.alx.common.domain.model

import jakarta.persistence.Embeddable
import org.jmolecules.ddd.annotation.ValueObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

private const val EUR = "EUR"

@Embeddable //TODO we dependent on persistence in domain model which is not good
@ValueObject
data class Money @Default constructor(
    var amount: BigDecimal,
    var currencyCode: String,
) {
    companion object {
        val DEFAULT_CURRENCY: Currency = Currency.getInstance(EUR)
        val DEFAULT_ROUNDING_MODE: RoundingMode = RoundingMode.HALF_EVEN
        val ZERO = Money(0)
    }

    init {
        this.amount = amount.setScale(2, DEFAULT_ROUNDING_MODE)
    }

    constructor(amount: Double) : this(BigDecimal(amount), DEFAULT_CURRENCY.currencyCode)
    constructor(amount: Int) : this(BigDecimal(amount), DEFAULT_CURRENCY.currencyCode)
    constructor(amount: BigDecimal) : this(amount, DEFAULT_CURRENCY.currencyCode)

    operator fun plus(money: Money): Money {
        return Money(amount + money.amount)
    }

    operator fun minus(money: Money): Money {
        return Money(amount - money.amount)
    }

    operator fun times(money: Money): Money {
        return Money(amount * money.amount)
    }

    operator fun times(factor: Double): Money {
        return Money(amount * BigDecimal(factor))
    }

    operator fun times(number: Int): Money {
        return Money(amount * BigDecimal(number))
    }


    operator fun div(money: Money): Money {
        return Money(amount / money.amount)
    }


    operator fun compareTo(amount: Money): Int {
        return this.amount.compareTo(amount.amount)
    }

    override fun toString(): String {
        return "Money(amount=$amount, currencyCode='$currencyCode')"
    }

    operator fun div(i: Int): Money {
        return Money(this.amount.divide(BigDecimal(i), DEFAULT_ROUNDING_MODE))
    }
}
