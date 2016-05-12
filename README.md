# Java-Final-Project
This project is a coffeeshop app where you can order some drinks;
Here are some steps one can follow to run this app;


  Step 1: Change the qunatity and move to next cell in order to see the change in the qunatity and the corresponding total price.
  
  Step 2: Once you are done choosing the drink, hit Calculate the final Price of all the drinks; It gives you an invoice of your order which can be formatted as per the need.I kept it simple. Can be made beautiful and more infromative.
  
  Step 3: Hit Total sum to get the payable cash along with the Vat included;
  
  Step 4: Once you give the customer their final sum, the user can write each order into a report table;
  
  Step 5: After writting it to the report table, the user can reset the data to be able to place a new order;
  
  Step 6: Finally, you can quit the App;
  
  
  This App has a special admin section which gives you some privilegdes like add a drink, delete a drink  and update the price of current listed drink.
  Also, Get the view from the repport table on a particular date and also the intererting part-> you get to see the chart of all drinks sold on a particlar day;
  This chart can be used by the higher management for the business-analysis as which drink is more profitable and what changes they need to make if any drink is not selling enough
  
  Steps to use the Admin section: -
  
  * Step 1: Enter the Password-"JavaProject" and It would higlight everything; this also gives you authority to change the drink's price
  * Step 2: you can write a drink name and its price and add it to the list;
  * Step 3: You can delete a drink in this section;
  * Step 4: Apart from this, YOu can get a view from the report table on a particular date; can be helpful to see what all drinks were sold on particular day
  * Step 5: Type a date into the search box to get the data;
  * Step 6: Once you get the view, you can write it into a text file if need be or you can get the chart to get visual represntation
  * Step 7: you can hit quitAdmin section to exit the adminSection;
  
Finally, the data can be extract not only from the report table but also from the drink table; this would give you an idea about the total sale for each drink till date.

Also, the search string is hard-coded to a date but can be changed to any string with prepared statement.

all validation done; no bugs found except for one, sometimes when you hit on chart button when there is no view, there is an error message like->
Class not found; this does not appear all the time but sometimes;
