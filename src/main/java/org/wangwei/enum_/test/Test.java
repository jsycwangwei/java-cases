package org.wangwei.enum_.test;

public class Test {

    public Test() {

    }

    private static Order getOrderByType(String type) {
        OrderType orderType = null;
        try {
            orderType = OrderType.valueOf(type.toUpperCase());
        }
        catch (Exception e) {
            orderType = OrderType.OTHER;
        }

        return orderType.getOrder();

    }

    public static void main(String[] args) {
        String type = "cari";
        int quantity = 1000;
        double singlePrice = 2.0;

        double totalPrice = getOrderByType(type).calculateTotalPrice(singlePrice, quantity);

        System.out.println(totalPrice);
    }
}
