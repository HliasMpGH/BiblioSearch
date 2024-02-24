<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="errorpage.jsp"%>
<%@ page import="gr.bibliotech.*" %>
<%@ page import="java.util.List" %>

<%

    User current_user = (User) session.getAttribute("userBtech");


    String query = request.getParameter("query");
    String filter = request.getParameter("filter");
    String genre = request.getParameter("genre");

    List<Book> matchingBooks = (!query.trim().isEmpty() ? Book.getMatchingBooks(query) : Book.getBooks());

    if (filter.equals("popular")) {
        matchingBooks = Book.getMostPopularBooks();
    } else if (filter.equals("customized")) {
        matchingBooks = current_user.getPreferedBooks();
    }

    
    if (!genre.equals("any")) {
        matchingBooks = Book.filterBooks(matchingBooks, genre);
    }

    request.setAttribute("results", matchingBooks);
%>

<jsp:forward page="booksearch.jsp"/>
