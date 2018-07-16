package com.sure.pure.model;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by v-62 on 10/19/2016.
 */


public class Product implements Serializable{
    String id;
    String Description;
    String Name;
    String prd_img;
    String Price;
    String offerprice;

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    String Category;
     String totalprice;
   // int drawables[];

    public Product()
    {

    }

    public String getProduct_id() {
        return id;
    }

    public void setProduct_id(String product_id) {
        this.id = product_id;
    }

    public String getProductdes() {
        return Description;
    }

    public void setProductdes(String productdes) {
        this.Description = productdes;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

    public Product(String Category,String id, String Name, String prd_img, String Price, String offerprice, String Description, String totalprice)
    {
        this.Category=Category;
        this.Name=Name;
        this.prd_img=prd_img;
        this.Price=Price;
        this.offerprice=offerprice;
        this.id=id;
        this.Description=Description;

        this.totalprice=totalprice;



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
        return Name;
    }

    public void setProductname(String productname) {
        this.Name = productname;
    }

    public String getProductimage() {
        return prd_img;
    }

    public void setProductimage(String prd_img) {
        this.prd_img = prd_img;
    }

    public String getSellerprice() {
        return Price;
    }

    public void setSellerprice(String sellerprice) {
        this.Price = sellerprice;
    }


    /*
     * Comparator implementation to Sort Order object based on Amount
     */
    public static class OrderByAmount implements Comparator<Product> {

        @Override
        public int compare(Product o1, Product o2) {

            return Integer.parseInt(o1.getSellerprice()) > Integer.parseInt(o2.getSellerprice()) ? 1 : (Integer.parseInt(o1.getSellerprice()) < Integer.parseInt(o2.getSellerprice()) ? -1 : 0);
        }
    }


    public static class OrderByAmountdouble implements Comparator<Product> {

        @Override
        public int compare(Product p1, Product p2) {
            if (Double.parseDouble(p1.getSellerprice()) < Double.parseDouble(p2.getSellerprice())) return -1;
            if (Double.parseDouble(p1.getSellerprice()) > Double.parseDouble(p2.getSellerprice())) return 1;
            return 0;
        }
    }


    public static class OrderByQuantityHigh implements Comparator<Product> {

        @Override
        public int compare(Product p1, Product p2) {
            if (p1.getQuantity() < p2.getQuantity()) return -1;
            if (p1.getQuantity() > p2.getQuantity()) return 1;
            return 0;
        }
    }

    public static class OrderByQuantityLow implements Comparator<Product> {

        @Override
        public int compare(Product p1, Product p2) {
            if (p1.getQuantity() < p2.getQuantity()) return -1;
            if (p1.getQuantity() > p2.getQuantity()) return 1;
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
