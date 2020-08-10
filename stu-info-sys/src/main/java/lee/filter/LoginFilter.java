package lee.filter;

import lee.model.Response;
import lee.util.JSONUtil;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String uri = req.getServletPath();
        HttpSession session = req.getSession(false);

        if (session == null) {
//            req.setCharacterEncoding("utf-8");
//            resp.setCharacterEncoding("utf-8");
            //首页重定向登录
            if (uri.equals("/public/page/main.html")) {

                resp.setContentType("text/html; charset=utf-8");
                final String scheme = req.getScheme();//http
                final String serverName = req.getServerName();//服务器ip
                final int serverPort = req.getServerPort();//端口号
                final String contextPath = req.getContextPath();//项目部署路径 sis上下文
                String basePath = scheme + "://" + serverName + ":" + serverPort + contextPath + "/public/index.html";
                resp.sendRedirect(basePath);
                return;
            //后端非登录接口 返回401
            } else if (!uri.startsWith("/public/") && !uri.startsWith("/static/")
                       && !uri.equals("/user/login")) {

                resp.setContentType("application/json");
                PrintWriter pw = resp.getWriter();
                Response r = new Response();
                r.setCode("ERR401");
                r.setMessage("不允许访问");
                resp.setStatus(401);
                pw.println(JSONUtil.write(r));
                pw.flush();
                return;
            }
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
