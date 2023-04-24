# InventoryDash
The InventoryDash was a study in creating an android application which met the following criteria:
<br>&emsp;- Contains a database with a minimum of two tables.
<br>&emsp;- Contains a login flow for a user where they can create an account and login.
<br>&emsp;- Contains a main inventory page where all of a user's inventory items are displayed.
<br>&emsp;- Contains a mechanism through which users can add an item to inventory.
<br>&emsp;- Contains a mechanism through which a user can increment or deincrement an inventory's qoh.
<br>&emsp;- Contains a mechanism through which the application can notify the user if inventory of an item has decreased to 0.
<br>
<br>The InventoryDash contains a numbe of main pages as detailed below:
<br>&emsp;- A landing page where a user can select to login or signup. This page can inflate the following alertDialogs:
<br>&emsp;&emsp;-Login
<br>&emsp;&emsp;-Sign Up
<br>&emsp;- A main inventory page where a recycler view presents the user with a list of inventory items. When an inventory item is selected it should present the user with the following alertDialogs:
<br>&emsp;&emsp;- Edit item
<br>&emsp;&emsp;- Create item
<br>&emsp;- A settings page where the user can set their notifications preferences.
<br>
<br> The general flows and initial mockups were created in this <a href="https://www.figma.com/files/project/83955400/InTrack?fuid=1212542451502772704">figma collection</a>. 
This UI tried to identify a target user base which the basic but customizable inventory management platform could properly serve.
It identified common pain points for small business creatives regarding tracking inventory and reducing overhead. 
Although the finished product is significantly simpler than intended, it does accomplish the sought after goals.
<br>
<br> The architectural design followed a rough MVC design process, with defined object models and Data Access Objects. 
This approach was for ther most part successful, although can certainly be refined to follow better coding practices. 
The design pattern was useful for the intended purpose, and I intend to grow upon it in future iterations of the project to try to reach MVP functionality.
<br> 
<br>A test driven development approach was taken where tests were written, and methods were written to ensure that the business logic and code itself were achieving the intended use case.
This process allowed for refactoring and refinement of individual pieces while still ensuring that the code remained functional.
<br>
<br>The largest innovation that occured during the development process was extracting core user needs and simplyfying the application to meet those needs, and only those needs.
This occurred frequently throughout the development process, and oddly the simpler, cleaner application was much more appealing than the original designs. 
<br>
<br>I believe the design of the application and flow are incredibly intuitive for users, and were a great success for the project. 
