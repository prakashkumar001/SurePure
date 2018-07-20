package com.sure.pure.pojo;

import com.sure.pure.model.Product;

import java.util.ArrayList;

public class ProductData
{
  public ProductData(String product_id,String product_price,int product_quantity,String product_total,String product_name)
  {
    this.product_id=product_id;
    this.product_name=product_name;
    this.product_price=product_price;
    this.product_quantity=product_quantity;
    this.product_total=product_total;

  }
  private String product_id;

  public String getProduct_total() {
    return product_total;
  }

  public void setProduct_total(String product_total) {
    this.product_total = product_total;
  }

  private String product_total;

  public String getProductId() { return this.product_id; }

  public void setProductId(String product_id) { this.product_id = product_id; }

  private String product_price;

  public String getProductPrice() { return this.product_price; }

  public void setProductPrice(String product_price) { this.product_price = product_price; }

  private int product_quantity;

  public int getProductQuantity() { return this.product_quantity; }

  public void setProductQuantity(int product_quantity) { this.product_quantity = product_quantity; }

  private String product_name;

  public String getProductName() { return this.product_name; }

  public void setProductName(String product_name) { this.product_name = product_name; }
}

