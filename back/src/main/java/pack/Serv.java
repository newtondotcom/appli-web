package pack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;

import jakarta.servlet.annotation.WebServlet;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vue.Facade;

@WebServlet("/Serv")
public class Serv extends HttpServlet {

  @EJB
  Facade facade;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    throws ServletException, IOException {

  }
}
