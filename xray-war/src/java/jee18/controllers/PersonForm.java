/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jee18.controllers;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jee18.dao.IAccess;

/**
 *
 * @author okaracalik
 */
@WebServlet(name = "PersonForm", urlPatterns = {"/person-form"})
public class PersonForm extends HttpServlet {

    @EJB
    private IAccess personAccess;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/personForm.jsp");
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        PersonEntity pe = PersonEntity.newInstance();
//        String firstName = request.getParameter("firstName");
//        String lastName = request.getParameter("lastName");
//        String dateOfBirth = request.getParameter("dateOfBirth");
//        String emailAddress = request.getParameter("emailAddress");
//        
//        pe.setFirstName(firstName);
//        pe.setLastName(lastName);
//        pe.setDateOfBirth(LocalDate.parse(dateOfBirth));
//        pe.setEmailAddress(emailAddress);
//        System.out.println(pe);
        response.sendRedirect("PersonList");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
