package com.Chitra;

/**
 * Created by chitrakakkar on 4/29/16.
 */
public class Drink
{
    String DrinksName;
    Double DrinkPrice;

   public Drink(String dn, Double dp)

    {
        this.DrinkPrice = dp;
        this.DrinksName = dn;
    }
    public String getDrinksName() {
        return DrinksName;
    }

    public void setDrinksName(String drinksName) {
        DrinksName = drinksName;
    }

    public Double getDrinkPrice() {
        return DrinkPrice;
    }

    public void setDrinkPrice(Double drinkPrice) {
        DrinkPrice = drinkPrice;
    }



}
