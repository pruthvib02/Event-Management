<%-- 
    Document   : AdminSecondaryNavbar.jsp
    Created on : 06-Mar-2024, 7:24:45 am
    Author     : shakthisravanth
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       
         <section class="options">
             <a href="Admin.jsp" class="book-button">Home</a> 
                    <a href="User.jsp" class="book-button">Users</a>
                    <a href="Booking.jsp" class="book-button">Bookings</a> 
                    <a href="History.jsp" class="book-button">History</a> 
        </section>
<!--        <section class="options">
                    <a href="Booking.jsp" class="book-button">Bookings</a> 
                    <a href="search.jsp" class="book-button">Search</a> 
        </section>-->
       
    


    <style>
        .options{
            
            padding: 20px;
            display: flex;
            justify-content: space-between;
        }
    .book-button {
            display: block;
            width: 100px;
            padding: 7px;
            background-color: #00ab9f;
            color: #fff;
            text-align: center;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .book-button:hover {
            background-color: #4fe8dd; 
        }
    </style>
    </body>
</html>
