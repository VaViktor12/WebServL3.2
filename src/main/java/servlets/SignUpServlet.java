package servlets;


import dbService.DBException;
import dbService.DBService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignUpServlet extends HttpServlet {
    private final DBService dbService;

    public SignUpServlet(DBService dbService) {
        this.dbService = dbService;
    }
    /*При получении POST запроса на signup сервлет SignUpServlet должн запомнить логин и пароль в AccountService.
    После этого польователь с таким логином считается зарегистрированным.
    Status code (200)
    и текст страницы:
    Authorized: login
    */
    //sign in
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");


        try {
            dbService.addUser(login,password);
        } catch (DBException e) {
            e.printStackTrace();
        }
    }


}
