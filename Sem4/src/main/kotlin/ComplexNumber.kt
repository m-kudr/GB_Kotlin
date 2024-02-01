
class ComplexNumber private constructor(val re: Double, val im: Double) {
    companion object {
        @JvmStatic
        fun create(re: Double, im: Double): ComplexNumber {
            return ComplexNumber(re, im)
        }

        @JvmStatic
        fun abs(c: ComplexNumber): Double {
            return kotlin.math.sqrt(c.re * c.re + c.im * c.im)
        }

        @JvmStatic
        @Throws(DivisionByZeroException::class)
        fun div(
            a: ComplexNumber, b: ComplexNumber
        ): ComplexNumber {
            if (b.re == 0.0 && b.im == 0.0) {
                throw DivisionByZeroException()
            }
            val divisor = b.re * b.re + b.im * b.im
            return create(
                (a.re * b.re + a.im * b.im) / divisor,
                (a.im * b.re - a.re * b.im) / divisor
            )
        }
    }
}