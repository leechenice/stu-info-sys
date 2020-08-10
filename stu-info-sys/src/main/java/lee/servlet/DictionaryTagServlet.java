package lee.servlet;

import lee.dao.DictionaryTagDAO;
import lee.model.DictionaryTag;
import lee.model.Response;
import lee.util.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/dict/tag/query")
public class DictionaryTagServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String key = req.getParameter("dictionaryKey");
        return DictionaryTagDAO.query(key);
    }
}