Quandoo Technical Task by Tihomir Vachkov
-----------------------------------------

I added some more things that were not requested and I need to make some comments about the app I submited. 

1. To store data permanently, I used SQlite database. 
   I added two buttons on the main screen - one called "Reset DB" and the other "Reset alarms". 
   "Reset DB" deletes the contents of the tables holding the information about customers and tables.
   To implement the scheduled deletion of the reservations I used AlarmManager and I store some information in a separate table.
   I called the schedule "alarm". The "Reset alarms" button deletes the  contents of the table "Alarms".
   
2. Another additional feature I thought of is a separate view of the tables. 
   In the bottom navigation there is a tab for viewing the tables. 
   Clicking on this tab, a screen displays the same GridView of the tables (showing the free tables) as when we choose a table for a customer.
   The purpose of this tab is only to show the status of the tables, but there is no way to change the availability of a table when clicking on a table.
   In other words, only the OnClickListeners are different.

3. The tables are enumerated starting from 1, not from 0. 
   This is why the first free table is in position 10 of the JSONArray, but is showing number 11 on the grid of tables.
   
4. The terms customer and reservation are synonyms in this scope. Both appear many times in the naming of pieces of code.


Thank you for your attention!
