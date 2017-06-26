package com.sure.pure.model;

import java.util.Comparator;

/**
 * Created by v-62 on 10/19/2016.
 */

public class Product {
    String product_id;
    String productdes;
    String productname;
    String productimage;
    String sellerprice;
    String offerprice;
   // int drawables[];

    public Product()
    {

    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProductdes() {
        return productdes;
    }

    public void setProductdes(String productdes) {
        this.productdes = productdes;
    }

    public Product(String product_id, String productname, String productimage, String sellerprice, String offerprice, String productdes)
    {
        this.productname=productname;
        this.productimage=productimage;
        this.sellerprice=sellerprice;
        this.offerprice=offerprice;
        this.product_id=product_id;
        this.productdes=productdes;



    }

   // static ArrayList<ArrayList<String>> stringList1=new ArrayList<ArrayList<String>>();

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    int quantity;

    public void setOfferprice(String offerprice) {
        this.offerprice = offerprice;
    }



    public String getOfferprice() {
        return offerprice;
    }



    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductimage() {
        return productimage;
    }

    public void setProductimage(String productimage) {
        this.productimage = productimage;
    }

    public String getSellerprice() {
        return sellerprice;
    }

    public void setSellerprice(String sellerprice) {
        this.sellerprice = sellerprice;
    }


    /*
     * Comparator implementation to Sort Order object based on Amount
     */
    public static class OrderByAmount implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {

            return Integer.parseInt(o1.getOfferprice()) > Integer.parseInt(o2.getOfferprice()) ? 1 : (Integer.parseInt(o1.getOfferprice()) < Integer.parseInt(o2.getOfferprice()) ? -1 : 0);
        }
    }


    public static class OrderByAmountdouble implements Comparator<Product> {

        @Override
        public int compare(Product p1, Product p2) {
            if (Double.parseDouble(p1.getOfferprice()) < Double.parseDouble(p2.getOfferprice())) return -1;
            if (Double.parseDouble(p1.getOfferprice()) > Double.parseDouble(p2.getOfferprice())) return 1;
            return 0;
        }
    }

    /*public static class OrderByDescending implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            return null;
        }
    }*/
    /*
     * Anohter implementation or Comparator interface to sort list of Order object
     * based upon customer name.
     */

    public static class OrderByCustomer implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {
            return o1.getProductname().compareTo(o2.getProductname());
        }
    }


    /*
      * Sorting on orderId is natural sorting for Order.
      */
   /* @Override
    public int compareTo(Product o) {
        return this.offerprice > o.offerprice ? 1 : (this.orderId < o.orderId ? -1 : 0);
    }*/

    /*
     * implementing toString method to print orderId of Order
     */
    /*@Override
    public String toString(){
        return String.valueOf(orderId);
    }
*/
}
