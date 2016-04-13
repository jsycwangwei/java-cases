package org.wangwei.enum_.test;

public abstract class Order implements ICalculate {

    protected final int quantityThreshold = 10000;

    public double queryDiscount(final int quantity) {
        if (quantity > quantityThreshold) {
            return queryDiscountMoreQuantityThreshold();
        }

        return queryDiscountLessQuantityThreshold();
    }

    public double calculateTotalPrice(final double singlePrice, final int quantity) {
        return singlePrice * queryDiscount(quantity) * quantity;
    }

    protected double queryDiscountMoreQuantityThreshold() {
        return 0.5;
    }

    protected double queryDiscountLessQuantityThreshold() {
        return 1.0;
    }

    protected OrderType getType() {
        return OrderType.OTHER;
    }

}
