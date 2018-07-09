package com.sure.pure.utils;

public class DrawerItem {
    private String Qty;

    private String Name;

    private String id;

    private String Description;

    private String Category;

    private String Price;

    private String Code;

    private String Rate;

    public String getQty ()
    {
        return Qty;
    }

    public void setQty (String Qty)
    {
        this.Qty = Qty;
    }

    public String getName ()
    {
        return Name;
    }

    public void setName (String Name)
    {
        this.Name = Name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDescription ()
    {
        return Description;
    }

    public void setDescription (String Description)
    {
        this.Description = Description;
    }

    public String getCategory ()
    {
        return Category;
    }

    public void setCategory (String Category)
    {
        this.Category = Category;
    }

    public String getPrice ()
    {
        return Price;
    }

    public void setPrice (String Price)
    {
        this.Price = Price;
    }

    public String getCode ()
    {
        return Code;
    }

    public void setCode (String Code)
    {
        this.Code = Code;
    }

    public String getRate ()
    {
        return Rate;
    }

    public void setRate (String Rate)
    {
        this.Rate = Rate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Qty = "+Qty+", Name = "+Name+", id = "+id+", Description = "+Description+", Category = "+Category+", Price = "+Price+", Code = "+Code+", Rate = "+Rate+"]";
    }
}