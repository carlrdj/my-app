/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import com.google.gson.Gson;
import dao.DaoItem;
import dao.impl.DaoItemImpl;
import dto.Item;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author carlr
 */
@WebServlet(name = "ItemServlet", urlPatterns = {"/item/*"})
public class ItemServlet extends HttpServlet {

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
        doGet(request, response);
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
        Gson gson = new Gson();
        DaoItem daoItem = new DaoItemImpl();
        String id = request.getPathInfo();
        String cadena = request.getQueryString();
        String cadena2 = request.getParameter("id");
        System.err.println(cadena);
        System.err.println(cadena2);
        String result;
        if (id == null) {
            String code = request.getParameter("code");
            code = (code == null)? "" : code;
            String name = request.getParameter("name");
            name = (name == null)? "" : name;
            result = gson.toJson(daoItem.getItems(code, name));
        } else {
            result = gson.toJson(daoItem.getItem(Integer.parseInt(id.substring(1))));
        }
        setAccessControlHeaders(response);
        try (PrintWriter out = response.getWriter()) {
            out.println(result);
        }
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
        Gson gson = new Gson();
        DaoItem daoItem = new DaoItemImpl();
        String result;
        try {
            
            Item item = gson.fromJson(request.getReader(), Item.class);
            if (daoItem.insertItem(item)) {
                result = "{\"msg\": \"Se registró correctamente.\", \"type\":\"is-success\"}";
            } else {
                result = "{\"msg\": \"Error al intentar registrar.\", \"type\":\"is-danger\"}";
            }
        } catch (Exception e) {
            result = "{\"msg\": \"Error! Seleccione familia y sub familia.\", \"type\":\"is-danger\"}";
        }
        setAccessControlHeaders(response);
        try (PrintWriter out = response.getWriter()) {
            out.println(result);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        DaoItem daoItem = new DaoItemImpl();
        String result;
        Item item = gson.fromJson(request.getReader(), Item.class);
        if (daoItem.updateItem(item)) {
            result = "{\"msg\": \"Se modificó correctamente.\", \"type\":\"is-success\"}";
        } else {
            result = "{\"msg\": \"Error al intentar modificar.\", \"type\":\"is-danger\"}";
        }
        setAccessControlHeaders(response);
        try (PrintWriter out = response.getWriter()) {
            out.println(result);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        DaoItem daoItem = new DaoItemImpl();
        String result;
        
        String id = request.getParameter("id");
        String status = request.getParameter("status");
        System.err.println("s" + id + " - " + status);
        
        if (daoItem.enableItem(Integer.parseInt(id), Integer.parseInt(status))) {
            if (status == "1") {
                result = "{\"msg\": \"Se habilitó correctamente.\", \"type\":\"is-success\"}";
            } else {
                result = "{\"msg\": \"Se inhabilitó correctamente.\", \"type\":\"is-success\"}";
            }
        } else {
            if (status == "1") {
                result = "{\"msg\": \"Error al habilitar.\", \"type\":\"is-success\"}";
            } else if (status == "0") {
                result = "{\"msg\": \"Error al inhabilitar.\", \"type\":\"is-success\"}";
            } else {
                result = "{\"msg\": \"Error.\", \"type\":\"is-success\"}";
            }
        }
        setAccessControlHeaders(response);
        try (PrintWriter out = response.getWriter()) {
            out.println(result);
        }
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

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, DELETE, GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
