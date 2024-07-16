package com.ddd.order.command.domain;

public enum OrderState {


    PAYMENT_WAITING("결제 대기중"), PREPARING("제품 준비중") {
        public boolean isShippingChangeable() {
            return true;
        }

        public boolean isOrderCancelable() {
            return true;
        }
    },

//    PREPARING("제품 준비중") {
//        public boolean isShippingChangeable() {
//            return true;
//        }
//
//        public boolean isOrderCancelable() {
//            return true;
//        }
//    },

    SHIPPED("출고 처리중"), DELIVERING("배송중"), DELIVERY_COMPLETED("배송 완료"), CANCEL("주문 취소");

    private final String message;

    OrderState(String message) {
        this.message = message;
    }


    public boolean isShippingChangeable() {
        return false;
    }

    public boolean isOrderCancelable() {
        return false;
    }
}
