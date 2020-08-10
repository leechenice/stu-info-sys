package lee.servlet;

import lee.dao.UserDAO;
import lee.model.User;
import lee.util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/user/login")
public class LoginServlet extends AbstractBaseServlet{
    @Override
    protected Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = JSONUtil.read(req.getInputStream(),User.class);
        User query = UserDAO.query(user);
        if(query == null)
            throw new RuntimeException("用户名或密码错误");
        HttpSession session = req.getSession();
        session.setAttribute("user",query);
        return null;
    }
}
