package cn.lcvc.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Allow-Origin","*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");
        httpServletResponse.setHeader("Access-Control-Max-Age","3600");
        httpServletResponse.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
        httpServletResponse.setHeader("","");httpServletResponse.setHeader("","");
        filterChain.doFilter(servletRequest,httpServletResponse);
    }

    @Override
    public void destroy() {

    }
}
