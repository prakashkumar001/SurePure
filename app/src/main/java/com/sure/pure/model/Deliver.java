package com.sure.pure.model;

/**
 * Created by Creative IT Works on 19-Jun-17.
 */

public class Deliver {
    public String order_id;
    public String status;
    public String prd_name;
    public String prd_price;

    public Deliver(String order_id, String status, String prd_name, String prd_price, String prd_quantity, String payment_date, String payment_type) {
        this.order_id = order_id;
        this.status = status;
        this.prd_name = prd_name;
        this.prd_price = prd_price;
        this.prd_quantity = prd_quantity;
        this.payment_date = payment_date;
        this.payment_type = payment_type;
    }

    public String prd_quantity;
    public String payment_date;
    public String payment_type;


}
