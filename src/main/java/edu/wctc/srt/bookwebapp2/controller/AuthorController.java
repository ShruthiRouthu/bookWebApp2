package edu.wctc.srt.bookwebapp2.controller;


import edu.wctc.srt.bookwebapp2.model.Author;
import edu.wctc.srt.bookwebapp2.model.AuthorDAO;
import edu.wctc.srt.bookwebapp2.model.AuthorDAOStrategy;
import edu.wctc.srt.bookwebapp2.model.AuthorDAOUsingConnectionPool;
import edu.wctc.srt.bookwebapp2.model.DBStrategy;
import edu.wctc.srt.bookwebapp2.model.MySqlDbStrategy;
import edu.wctc.srt.bookwebapp2.model.AuthorService;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    // NO MAGIC NUMBERS!

    private static final String NO_PARAM_ERR_MSG = "No request parameter identified";
    private static final String LIST_PAGE = "/listAuthors.jsp";
    private static final String MANAGE_PAGE = "/manageAuthors.jsp";
    private static final String EDIT_PAGE = "/editAuthor.jsp";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String EDIT_ACTION = "edit";
    private static final String DELETE_ACTION = "delete";
    private static final String SHOW_EDITPAGE_ACTION = "showEditPage";
    private static final String ACTION_PARAM = "action";
    private static final String MANAGE_ACTION = "manage";
    
    
    // variables to hold data from xml
    String authorDAOStrategyClassName ;
    String dbStrategyClassName ;
    String driverClass ;
    String url;
    String user ;
    String password ;
    
    DBStrategy dbStrategy ;
    AuthorDAOStrategy authorDAOStrategy;
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String destination = LIST_PAGE;
        String action = request.getParameter(ACTION_PARAM);

         
//        DBStrategy db = new MySqlDbStrategy();
//        AuthorDAOStrategy authDao
//                = new AuthorDAO(db, "com.mysql.jdbc.Driver",
//                        "jdbc:mysql://localhost:3306/book", "root", "DJ2015");
     
//       AuthorService authService = new AuthorService(authDao);
        
        AuthorService authService = null;
        System.out.println("Shruthi 1");
        try{ 
            System.out.println("Shruthi 2");
            authService = getAuthorService();
        }
        catch (Exception ex)
                {
                    System.out.println("Didnot get service from method !");
                    System.out.println(ex.getCause().getMessage());
                }

      
        try {
            if (action.equals(LIST_ACTION)) {
                List<Author> authors = null;
                authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = LIST_PAGE;

            } else if (action.equals(ADD_ACTION)) {
                String name  = request.getParameter("authorName");
                String dateAdded = request.getParameter("dateAdded");
                if((name != null) && (dateAdded != null))
                {
                    List<String> key = new ArrayList();
                    key.add( "author_name");
                    key.add("date_added");
                
                    List<Object> value = new ArrayList();
                    value.add(name);
                    value.add(dateAdded);
                    
                    authService.insertAuthor(key, value);
                                  
                }
                
                List<Author> authors = null;
                authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = MANAGE_PAGE;
                
                
            } else if (action.equals(DELETE_ACTION)) {
                String id  = request.getParameter("authorID");
                if(id != null)
                {
                    authService.deleteAuthorByID("author_id", id);
                }
                
                List<Author> authors = null;
                authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = MANAGE_PAGE;
                               
            } else if (action.equals(SHOW_EDITPAGE_ACTION)) {
                String id  = request.getParameter("authorID");
                 if(id != null)
                {
                    Author author = authService.getAuthorByID("author_id", id);
                    if(author != null)
                    {
                        request.setAttribute("selectedAuth", author);
                        destination = EDIT_PAGE;
                    }
                }
            
            }else if (action.equals(EDIT_ACTION)) {
            
                String name  = request.getParameter("authorName");
                String dateAdded = request.getParameter("dateAdded");
                String id = request.getParameter("authorID"); 
                                
                List<String> key = new ArrayList();
                key.add( "author_name");
                key.add("date_added");
                
                List<Object> value = new ArrayList();
                value.add(name);
                value.add(dateAdded);
                
                authService.updateAuthor("author_id", id , key, value);
              
                List<Author> authors = null;
                authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = MANAGE_PAGE;
                
            } else if (action.equals(MANAGE_ACTION)) { 
                
                List<Author> authors = null;
                authors = authService.getAllAuthors();
                request.setAttribute("authors", authors);
                destination = MANAGE_PAGE;                      
            }else {
                // no param identified in request, must be an error
                request.setAttribute("errMsg", NO_PARAM_ERR_MSG);
                destination = LIST_PAGE;
            }
            
        } catch (Exception e) {
            request.setAttribute("errMsg", e.getCause().getMessage());
        }

        // Forward to destination page
        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(destination);
        dispatcher.forward(request, response);
    }
    
    private AuthorService getAuthorService() throws Exception {
    
        AuthorService authorService = null;
        //System.out.println("Shruthi ");
        try {
            // get dbstrategy set up
            Class dbClassName = Class.forName(dbStrategyClassName) ;
            dbStrategy = (DBStrategy)dbClassName.newInstance();   // here we are casting but casting it to 
                                                                  // interface not concrete class
            
            // get authordao set up
            Class daoClassName = Class.forName(authorDAOStrategyClassName);
            
            Constructor constructor = null ;
            
            try{
            constructor = daoClassName.getConstructor(new Class[] {DBStrategy.class, String.class,
                String.class ,String.class, String.class});
            } catch (NoSuchMethodException e) {}
            
            if(constructor != null){
                Object[] argsForConstructor = new Object[] {dbStrategy, driverClass, url, user, password};
                authorDAOStrategy = (AuthorDAOStrategy)constructor.newInstance(argsForConstructor);  
                authorService =  new AuthorService(authorDAOStrategy);
            }
            else {
                // Do something for connection pool
                // Find the connection pool and create the DataSource     
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("jdbc/book");

                authorDAOStrategy = new AuthorDAOUsingConnectionPool(new MySqlDbStrategy(),ds);
                authorService =  new AuthorService(authorDAOStrategy);
            }
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }
        
        return authorService;
       
    }
    
   
    
    @Override
    public void init() throws ServletException {
        
        authorDAOStrategyClassName = getServletConfig().getInitParameter("authorDAO");
        dbStrategyClassName = getServletConfig().getInitParameter("dbStrategy");
        driverClass = getServletConfig().getInitParameter("driverClass");
        url = getServletConfig().getInitParameter("url");
        user  = getServletConfig().getInitParameter("userName"); 
        password = getServletConfig().getInitParameter("password"); 
        
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
