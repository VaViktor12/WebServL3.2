package main;

import dbService.DBService;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlets.SignInServlet;
import servlets.SignUpServlet;
//решение в рамках задания
//https://stepik.org/lesson/12405/step/15?unit=2835
//
/*Часть первая (аналогично заданию 2)
Написать сервер с двумя сервлетами:
SignUpServlet для обработки запросов на signup и
SignInServlet для обработки запросов на signin

Сервлеты должны слушать POST запросы с параметрами
login
password

При получении POST запроса на signup сервлет SignUpServlet должн запомнить логин и пароль в AccountService.
После этого польователь с таким логином считается зарегистрированным.
При получении POST запроса на signin, после регистрации, SignInServlet проверяет,
логин/пароль пользователя. Если пользователь уже зарегистрирован, север отвечает

Status code (200)
и текст страницы:
Authorized: login

если нет:
Status code (401)
текст страницы:
Unauthorized

Часть вторая (работа с базой)
Для запоминания пользователя AccountService должен использовать базу данных.
Для теста используйте базу H2 над файлом в той же директории, что и src

            String url = "jdbc:h2:./h2db";
            String name = "test";
            String pass = "test";

            JdbcDataSource ds = new JdbcDataSource();
            ds.setURL(url);
            ds.setUser(name);
            ds.setPassword(pass);

Для хранения данных пользователя используйте таблицу users:
create table if not exists users (id bigint auto_increment, login varchar(256), password varchar(256), primary key (id));
Сервер должен создавать таблицу при старте если она не существует.

При получении запроса на signup сервлет должен обратиться к DBService и записать логин и пароль в таблицу.*/

public class Main {
    public static void main(String[] args) throws Exception {
        DBService dbService = new DBService();


        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignInServlet(dbService)), "/signin");
        context.addServlet(new ServletHolder(new SignUpServlet(dbService)), "/signup");

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});

        Server server = new Server(8080);
        server.setHandler(handlers);

        server.start();
        System.err.println("Server started");
        server.join();

    }
}
