package org.wangwei.enum_.test;

public class Orders {
    /**
     * Some Orders ******
     */

    public class ROrder extends Order {

        @Override
        protected double queryDiscountLessQuantityThreshold() {
            // TODO Auto-generated method stub
            return 0.8;
        }

        @Override
        protected OrderType getType() {
            // TODO Auto-generated method stub
            return OrderType.R;
        }

    }

    public class CANIOrder extends Order {

        @Override
        protected double queryDiscountLessQuantityThreshold() {
            // TODO Auto-generated method stub
            return 0.8;
        }

        @Override
        protected OrderType getType() {
            // TODO Auto-generated method stub
            return OrderType.CANI;
        }
    }

    public class CARIOrder extends Order {
        @Override
        protected double queryDiscountLessQuantityThreshold() {
            // TODO Auto-generated method stub
            return 0.8;
        }

        @Override
        protected OrderType getType() {
            // TODO Auto-generated method stub
            return OrderType.CARI;
        }
    }

    public class SOrder extends Order {
        @Override
        protected double queryDiscountLessQuantityThreshold() {
            // TODO Auto-generated method stub
            return 0.9;
        }

        @Override
        protected OrderType getType() {
            // TODO Auto-generated method stub
            return OrderType.S;
        }
    }

    public class WOrder extends Order {
        @Override
        protected double queryDiscountLessQuantityThreshold() {
            return 0.9;
        }

        @Override
        protected OrderType getType() {
            // TODO Auto-generated method stub
            return OrderType.W;
        }
    }

    public class NAOrder extends Order {

        private double naSinglePrice = 100;

        @Override
        protected double queryDiscountLessQuantityThreshold() {
            return super.queryDiscountLessQuantityThreshold();
        }

        @Override
        protected OrderType getType() {
            // TODO Auto-generated method stub
            return OrderType.NA;
        }

        @Override
        public double calculateTotalPrice(double singlePrice, int quantity) {
            return super.calculateTotalPrice(naSinglePrice, quantity);
        }

    }

    public class OtherOrder extends Order {
        @Override
        protected double queryDiscountLessQuantityThreshold() {
            return super.queryDiscountLessQuantityThreshold();
        }

        @Override
        protected OrderType getType() {
            // TODO Auto-generated method stub
            return super.getType();
        }
    }
}
