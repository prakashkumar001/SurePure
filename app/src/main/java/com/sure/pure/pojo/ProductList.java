package com.sure.pure.pojo;

import java.util.ArrayList;

public class ProductList
{
  private ArrayList<ProductData> productData;

  public ArrayList<ProductData> getProductData() { return this.productData; }

  public void setProductData(ArrayList<ProductData> productData) { this.productData = productData; }

  private String emailid;

  public String getEmailid() { return this.emailid; }

  public void setEmailid(String emailid) { this.emailid = emailid; }
}
