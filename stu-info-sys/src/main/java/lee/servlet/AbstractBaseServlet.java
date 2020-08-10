package lee.servlet;

import lee.dao.DictionaryTagDAO;
import lee.model.DictionaryTag;
import lee.model.Response;
import lee.util.JSONUtil;
import lee.util.ThreadLocalHolder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public abstract class AbstractBaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");

        Response r = new Response();


        try {
            Object o = process(req,resp);
            r.setCode("COK200");
            r.setMessage("操作成功");
            r.setTotal(ThreadLocalHolder.getTOTAL().get());
            r.setSuccess(true);
            r.setData(o);
        }catch (Exception e) {
            r.setSuccess(false);
            r.setMessage(e.getMessage());
            r.setCode("ERR500");
            StringWriter sw = new StringWriter();
            PrintWriter writer = new PrintWriter(sw);
            e.printStackTrace(writer);
            final String st = sw.toString();
            System.err.println(st);
            r.setStackTrace(st);
        }finally {
            ThreadLocalHolder.getTOTAL().remove();
        }
        PrintWriter printWriter = resp.getWriter();
        printWriter.println(JSONUtil.write(r));
        printWriter.flush();
    }

    protected abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception;


}
