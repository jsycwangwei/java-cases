package org.wangwei.enum_.test;

public enum OrderType implements IOrderType {

    R {
        @Override
        public Order generateOrder() {
            // TODO Auto-generated method stub
            return new Orders().new ROrder();
        }
    },
    CANI {
        @Override
        public Order generateOrder() {
            // TODO Auto-generated method stub
            return new Orders().new CANIOrder();
        }
    },
    CARI {
        @Override
        public Order generateOrder() {
            // TODO Auto-generated method stub
            return new Orders().new CARIOrder();
        }
    },
    W {
        @Override
        public Order generateOrder() {
            // TODO Auto-generated method stub
            return new Orders().new WOrder();
        }
    },
    S {
        @Override
        public Order generateOrder() {
            // TODO Auto-generated method stub
            return new Orders().new SOrder();
        }
    },
    NA {
        @Override
        public Order generateOrder() {
            // TODO Auto-generated method stub
            return new Orders().new NAOrder();
        }
    },
    OTHER {
        @Override
        public Order generateOrder() {
            // TODO Auto-generated method stub
            return new Orders().new OtherOrder();
        }
    };

    Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order generateOrder() {
        // TODO Auto-generated method stub
        return null;
    }

    private OrderType() {
        order = generateOrder();
    }

}
