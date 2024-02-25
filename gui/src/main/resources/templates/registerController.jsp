<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="errorpage.jsp"%>
<%@ page import="gr.bibliotech.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="header.jsp" %>
    <link rel="stylesheet" href="css/registerstyle.css">
    <title>Registration</title>

</head>
<body>

            <jsp:include page="navbar.jsp" />

<% 
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    String email = request.getParameter("email");
    boolean isValid = true;
    String[] valid = new String[8];
    int i = 0;

    if (username != null && !username.isEmpty()) {
        if (username.length() < 4) {
            isValid = false;
            valid[i] = "Username needs to be at least 4 Characters long.";
            i++;
        }
    } else {
        isValid = false;
        valid[i] = "Don't forget your username!";
        i++;
    }

    if (email != null && !email.isEmpty()) {
        if (email.length() < 8) {
            isValid = false;
            valid[i] = "Email needs to be at least 8 Characters long.";
            i++;
        }
    } else {
        isValid = false;
        valid[i] = "Don't forget your email!";
        i++;
    }

    if (password != null && !password.isEmpty()) {
        if (password.length() < 8) {
            isValid = false;
            valid[i] = "Password needs to be at least 8 Characters long.";
            i++;
        }
    } else {
        isValid = false;
        valid[i] = "Don't forget your password!";
        i++;
    }

    // if (!agree.isEmpty()) {
    //     isValid = false;
    //     valid[i] = "Please, agree with our terms!";
    //     i++;
    // }

    if (isValid) {
        boolean userExists = User.existsUserName(username);
        boolean emailExists = User.existsUserMail(email);
        if (userExists || emailExists) {
            isValid = false;
            valid[i] = "This username or email is taken. Please, choose another one!";
            i++;
        } else {
            User newUser = new User(username, email, password);
        }
    }
    %>

    <% if (isValid) {  %> 

    <div class="card mx-auto mt-6 mb-8" style="border-radius:15px;background-color: #E8FCB0;width:40%;height:20%;margin-left:25%;margin-top:10%;font-family:'Quicksand',sans-serif">
        <div class="card-header">
           <h2> Registration Complete! </h2>
            </div>
        <div class="card-body">
            <h4 class="card-title"><strong>Welcome, <%=username%></strong></h4>
            <p class="card-text">We are so excited for you to join us!</p>
            <small class="card-text">Feel free to browse in our website! </small>
            <a href="bookclubspage.jsp" class="btn btn-danger">BookClubs</a>
            <a href="forumspage.jsp" class="btn btn-danger">Forums</a>
        </div>
    </div>

<% } else { %>
    <div class="card mx-auto mt-6 mb-8" style="border-radius:15px;background-color: #E8FCB0;width:40%;height:20%;margin-left:25%;margin-top:10%;font-family:'Quicksand',sans-serif">
        <div class="card-header">
            <h2> Oops, we have some issues </h2> 
            </div>
        <div class="card-body">
         
            <h4 class="card-title"><strong>Please, take a look at your data!</strong></h4>
            <% for (int j=0;j<i; j++) { %>
            <p class="card-text"><%=valid[j] %></p>
                   <%     } %>
            <small class="card-text">Try again? </small>
            <a href="register.jsp" class="btn btn-danger">Register</a>
        </div>
    </div>
            

 <% } %>




</body>
</html>
