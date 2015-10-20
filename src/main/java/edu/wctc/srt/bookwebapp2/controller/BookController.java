/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wctc.srt.bookwebapp2.controller;

import edu.wctc.srt.bookwebapp2.entity.Author;
import edu.wctc.srt.bookwebapp2.entity.Book;
import edu.wctc.srt.bookwebapp2.service.AbstractFacade;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shruthi Routhu
 */
@WebServlet(name = "BookController", urlPatterns = {"/BookController"})
public class BookController extends HttpServlet {
    
    
    private static final String PAGE_BOOK_LIST = "/book_list.jsp";
    private static final String PAGE_INDEX = "/index.html";
    private static final String PARAM_ACTION = "action";
    private static final String ATTRIBUTE_BOOKS = "books";
    private static final String ACTION_SHOW_BOOK_LIST = "showBookList";
    @Inject
    private AbstractFacade<Book> bookService;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
      
        Book book  = null;
        String destination = null ;
        
        String action = request.getParameter(PARAM_ACTION);
        
        
            if (action.equals(ACTION_SHOW_BOOK_LIST)) {
                
                List<Book> books = null;
                books = bookService.findAll();
                request.setAttribute(ATTRIBUTE_BOOKS, books);
                destination = PAGE_BOOK_LIST;
            }    
       

       
        
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
        
    }    
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
