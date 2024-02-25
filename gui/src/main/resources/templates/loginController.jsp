<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="errorpage.jsp"%>
<%@ page import="gr.bibliotech.*" %>

<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    try {
        User validUser = User.authenticate(username, password);

        session.setAttribute("userBtech", validUser);
	    response.sendRedirect("mainpage.jsp");
    } catch(Exception e) {
        request.setAttribute("message", "Wrong username or password");
%>
        <jsp:forward page="login.jsp" />
<%
    }
%>    
