package pl.training.goodweather.commons

class Calculator {

    fun add(leftHandSide: Double, rightSideHand: Double) = leftHandSide + rightSideHand

    fun divide(leftHandSide: Double, rightSideHand: Double): Double {
        require(rightSideHand != 0.0)
        return leftHandSide / rightSideHand
    }

}