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
import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 *
 * @author Shruthi Routhu
 */
public class BookController extends HttpServlet {
    
    // PARAMETER constants
    private static final String PARAM_ACTION = "action";
    private static final String PARAM_BOOKID = "bookId";
    private static final String BOOK_ID = "book_id";
    private static final String PARAM_TITLE = "bookTitle" ;
    private static final String PARAM_ISBN = "bookIsbn";
    private static final String PARAM_AUTHORID ="authorId";  
           
    // ACTION constants
    private static final String ACTION_LIST_PAGE = "showListPage";
    private static final String ACTION_MANAGE_PAGE = "showManageBooks";
    private static final String ACTION_EDIT_PAGE = "showEditPage";
    private static final String ACTION_ADD_PAGE = "showAddPage";
    private static final String ACTION_HOME_PAGE = "showHomePage";
    private static final String ACTION_EDIT = "edit";
    private static final String ACTION_DELETE="delete";
    private static final String ACTION_ADD="add";
    
    // PAGE URL constants
    private static final String PAGE_LIST = "/book-list.jsp";
    private static final String PAGE_MANAGE = "/manage-books.jsp";
    private static final String PAGE_HOME = "/index.html";
    private static final String PAGE_EDIT = "/edit-book.jsp";
    private static final String PAGE_ERROR = "/error-page.jsp";
            
    // ATTRIBUTE constants
    private static final String ATTRIBUTE_BOOKS = "books" ;
    private static final String ATTRIBUTE_SELECTED_BOOK = "selectedBook";
    private static final String ATTRIBUTE_AUTHORS = "authors" ;
    private static final String ATTRIBUTE_SELECTED_AUTHOR = "selectedAuthor";
    
    @Inject
    private AbstractFacade<Book> bookService;
    @Inject
    private AbstractFacade<Author> authorService;

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
     
        String destination = "";
        int book_id;
        
        String action = request.getParameter(PARAM_ACTION);

        
        try{
                    
            switch(action){
                case ACTION_HOME_PAGE :
                    destination = PAGE_HOME;
                    break;
                    
                case ACTION_LIST_PAGE : 
                    resetBookList(request,bookService);
                    resetAuthorList(request, authorService);
                    destination = PAGE_LIST;
                    break;
                    
                case ACTION_MANAGE_PAGE :
                    resetBookList(request,bookService);
                    resetAuthorList(request, authorService);
                    destination = PAGE_MANAGE;
                    break;
                
                case ACTION_EDIT_PAGE :
                    book_id = getParameterBook_id(request);
                    Book selectedBook = bookService.find(book_id);
                    request.setAttribute(ATTRIBUTE_SELECTED_BOOK, selectedBook);
                    resetAuthorList(request,authorService);
                    destination = PAGE_EDIT;
                    break;
                    
                case ACTION_ADD_PAGE :  
                    resetAuthorList(request,authorService);
                    destination = PAGE_EDIT;
                    break;
                    
                case ACTION_DELETE :
                    book_id = getParameterBook_id(request);
                    if(book_id != -1){
                        selectedBook = bookService.find(book_id);
                        bookService.remove(selectedBook);
                    }
                    resetBookList(request,bookService);
                    destination = PAGE_MANAGE;
                    break;    
                
                case ACTION_EDIT : 
                case ACTION_ADD :    
                    book_id = getParameterBook_id(request);
                    String title  = request.getParameter(PARAM_TITLE);
                    String isbn  = request.getParameter(PARAM_ISBN);                
                    String authorId  = request.getParameter(PARAM_AUTHORID);
                    Author author = null;
                    if((authorId != null) && ( Integer.parseInt(authorId)!= -1 )){
                        author = authorService.find(Integer.parseInt(authorId));
                    }
                   
                    
                    
                    Book book = null ;                       
                    
                    // Logic to decide between INSERT or UPDATE
                    if(book_id == -1){ 
                        book = new Book(0);
                        book.setIsbn(isbn);
                        book.setTitle(title);
                        if(author != null){
                            book.setAuthorId(author);
                        }
                        bookService.create(book);
   
                    }                       
                    else{
                        book = bookService.find(new Integer(book_id)) ;
                        book.setIsbn(isbn);
                        book.setTitle(title);
                        if(author != null){
                            book.setAuthorId(author);
                        }
                        bookService.edit(book);
                    }
                
                    resetBookList(request,bookService);
                    resetAuthorList(request,authorService);
                    destination = PAGE_MANAGE;
                    break;
                
            }
            
        }catch(Exception e){
               destination = PAGE_ERROR ; 
               System.out.println(e.getMessage());
        }
        finally{    
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(destination);
            dispatcher.forward(request, response);
        }    
        
    }

    private int getParameterBook_id(HttpServletRequest request){
        int book_id = -1; //flag
        String temp = request.getParameter(PARAM_BOOKID);
        if(temp != null){
            book_id = Integer.parseInt(temp);
        }
        return book_id;
    }
    
    private void resetBookList(HttpServletRequest request, AbstractFacade<Book> bookService) throws SQLException,Exception{
        List<Book> books = bookService.findAll(); 
        request.setAttribute(ATTRIBUTE_BOOKS ,books);
    }
    
    private void resetAuthorList(HttpServletRequest request, AbstractFacade<Author> authorService) throws SQLException,Exception{
        List<Author> authors = authorService.findAll(); 
        request.setAttribute(ATTRIBUTE_AUTHORS ,authors);
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
