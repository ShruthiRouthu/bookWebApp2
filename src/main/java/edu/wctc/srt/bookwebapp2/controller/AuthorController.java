package edu.wctc.srt.bookwebapp2.controller;



import edu.wctc.srt.bookwebapp2.entity.Author;
import edu.wctc.srt.bookwebapp2.entity.Book;

import edu.wctc.srt.bookwebapp2.service.AuthorService;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

// THis is the latest app with JPI as on  23/10/2015

// Not using annotations bcause this servlet is configured using web.xml
public class AuthorController extends HttpServlet {


    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String MANAGE_PAGE = "/manageAuthors.jsp";
    private static final String EDIT_PAGE = "/editAuthor.jsp";
    private static final String HOME_PAGE = "/index.html";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String EDIT_ACTION = "edit";
    private static final String DELETE_ACTION = "delete";
    private static final String SHOW_EDITPAGE_ACTION = "showEditPage";
    private static final String ACTION_PARAM = "action";
    private static final String MANAGE_ACTION = "manage";
    private static final String HOME_ACTION = "home";
    private static final String FONT_COLOR = "fontColor";
    private static final String PAGE_COLOR = "pageColor";
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    // Session and context code------------------------------------
//        HttpSession session = request.getSession();
        //       ServletContext context = request.getServletContext();
//        // Getting DATA from form
//        String fontColor = request.getParameter(FONT_COLOR);
//        String pageColor = request.getParameter(PAGE_COLOR);
//    
//        if(fontColor != null && fontColor.length() > 0)
//        {
//            context.setAttribute(FONT_COLOR,fontColor);
//        }
//        
//        if(pageColor != null && pageColor.length() > 0)
//        {
//            session.setAttribute(PAGE_COLOR,pageColor);
//        }
        // Session and context code-----------------------------------
        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);

        Author author = null;

        /*
         This is how you inject a Spring service object into your servlet. Note
         that the bean name must match the name in your service class.
         */
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        AuthorService authService = (AuthorService) ctx.getBean("authorService");

        String id = null;
        try {
            if (action.equals(LIST_ACTION)) {

                List<Author> authors = null;
                authors = authService.findAll();
                request.setAttribute("authors", authors);
                destination = LIST_PAGE;

            } else if (action.equals(MANAGE_ACTION)) {

                List<Author> authors = null;
                authors = authService.findAll();
                request.setAttribute("authors", authors);
                destination = MANAGE_PAGE;

            } else if (action.equals(SHOW_EDITPAGE_ACTION)) {
                id = request.getParameter("authorID");
                if (id != null) {
                    author = authService.findByIdAndEagerLoadBooks(id);
                    if (author != null) {
                        Set<Book> bookSet = author.getBookSet();
                        request.setAttribute("bookSet", bookSet);
                        
                    } else {
                        author = authService.findById(id);
                        request.setAttribute("bookSet", new HashSet<>());
                    }
                    request.setAttribute("selectedAuth", author);
                    destination = EDIT_PAGE;
                }
            } else if (action.equals(ADD_ACTION)) {

                String name = request.getParameter("authorName");
                if (name != null) {
                    author = new Author(0);
                    author.setAuthorName(name);
                    author.setDateAdded(new Date());
                }
                authService.edit(author);
                List<Author> authors = null;
                authors = authService.findAll();
                request.setAttribute("authors", authors);
                destination = MANAGE_PAGE;

            } else if (action.equals(DELETE_ACTION)) {
                id = request.getParameter("authorID");
                if (id != null) {
                    author = authService.findById(id);
                    authService.remove(author);
                }

                List<Author> authors = null;
                authors = authService.findAll();
                request.setAttribute("authors", authors);
                destination = MANAGE_PAGE;

            } else if (action.equals(EDIT_ACTION)) {

                String name = request.getParameter("authorName");
                id = request.getParameter("authorID");

                author = authService.findById(id);
                author.setAuthorName(name);

                author.setDateAdded(new Date());
                authService.edit(author);
                List<Author> authors = authService.findAll();
                request.setAttribute("authors", authors);
                destination = MANAGE_PAGE;

            } else {
                destination = LIST_PAGE;
            }

        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        if (action.equals(HOME_ACTION)) {
            response.sendRedirect(sctx.getContextPath() + HOME_PAGE);
        } else {
            RequestDispatcher dispatcher
                    = getServletContext().getRequestDispatcher(destination);
            dispatcher.forward(request, response);
        }

    }
    
  /*  private AuthorService getAuthorService() throws Exception {
    
        AuthorService authorService = null;
        try {
            
            // get dbstrategy set up
            Class dbClassName = Class.forName(dbStrategyClassName) ;
            dbStrategy = (DBStrategy)dbClassName.newInstance();   // here we are casting but casting it to 
                                                                  // interface not concrete class
            
            // get authordao set up
            Class daoClassName = Class.forName(authorDAOStrategyClassName);
            
            Constructor constructor = null ;
            
            // so does it searche for constructor among all classes implementing AuthorDAOStrategy ?
            try{
            constructor = daoClassName.getConstructor(new Class[] {DBStrategy.class, String.class,
                String.class ,String.class, String.class}); // getConstructor doesnot return null but throws
            } catch (NoSuchMethodException e) {}            // an exception if it doesnot find the required constructor
            
            if(constructor != null){
                Object[] argsForConstructor = new Object[] {dbStrategy, driverClass, url, user, password};
                authorDAOStrategy = (AuthorDAOStrategy)constructor.newInstance(argsForConstructor);  
                authorService =  new AuthorService(authorDAOStrategy);
            }
            // this runs when using connection pools
            else { 
                Context context = new InitialContext();
                DataSource ds = (DataSource) context.lookup("jdbc/book");

                authorDAOStrategy = new AuthorDAOUsingConnectionPool(new MySqlDbStrategy(),ds);
                authorService =  new AuthorService(authorDAOStrategy);
            }
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }
        
        return authorService;
       
    } /*
 
    @Override
    public void init() throws ServletException {
        
       
        
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
