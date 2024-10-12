package com.homesharing.filter;

import com.homesharing.util.CookieUtil;
import jakarta.annotation.Priority;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter(urlPatterns = {"/*"})
@Priority(1)
public class AuthenticationFilter implements Filter {

    /**
     * <p>
     * Called by the web container to indicate to a filter that it is being placed into service.
     * </p>
     *
     * <p>
     * The servlet container calls the init method exactly once after instantiating the filter. The init method must
     * complete successfully before the filter is asked to do any filtering work. The container will ensure that actions
     * performed in the <code>init</code> method will be visible to any threads that subsequently call the
     * <code>doFilter</code> method according to the rules in JSR-133 (i.e. there is a 'happens before' relationship between
     * <code>init</code> and <code>doFilter</code>).
     * </p>
     *
     * <p>
     * The web container cannot place the filter into service if the init method either
     * </p>
     * <ol>
     * <li>Throws a ServletException
     * <li>Does not return within a time period defined by the web container
     * </ol>
     *
     * @param filterConfig a <code>FilterConfig</code> object containing the filter's configuration and initialization
     *                     parameters
     * @throws ServletException if an exception has occurred that interferes with the filter's normal operation
     * @implSpec The default implementation takes no action.
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    /**
     * The <code>doFilter</code> method of the Filter is called by the container each time a request/response pair is passed
     * through the chain due to a client request for a resource at the end of the chain. The FilterChain passed in to this
     * method allows the Filter to pass on the request and response to the next entity in the chain.
     *
     * <p>
     * A typical implementation of this method would follow the following pattern:
     * <ol>
     * <li>Examine the request
     * <li>Optionally wrap the request object with a custom implementation to filter content or headers for input filtering
     * <li>Optionally wrap the response object with a custom implementation to filter content or headers for output
     * filtering
     * <li>
     * <ul>
     * <li><strong>Either</strong> invoke the next entity in the chain using the FilterChain object
     * (<code>chain.doFilter()</code>),
     * <li><strong>or</strong> not pass on the request/response pair to the next entity in the filter chain to block the
     * request processing
     * </ul>
     * <li>Directly set headers on the response after invocation of the next entity in the filter chain.
     * </ol>
     *
     * @param request  the <code>ServletRequest</code> object contains the client's request
     * @param response the <code>ServletResponse</code> object contains the filter's response
     * @param chain    the <code>FilterChain</code> for invoking the next filter or the resource
     * @throws IOException      if an I/O related error has occurred during the processing
     * @throws ServletException if an exception occurs that interferes with the filter's normal operation
     * @see UnavailableException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Lấy thông tin từ URI của request
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        // Danh sách các URL muốn bỏ qua (ví dụ: trang login, register, và các tài nguyên tĩnh)
        List<String> excludedUrls = Arrays.asList("/login.jsp", "/register.jsp", "/css/", "/js/", "/images/","/home-page","/login","/logout","/signup","/staff-login","/verify","/sign-up-google", "/sign-up.jsp", "/home.jsp", "/header.jsp", "/footer.jsp", "/staff-login.jsp", "/terms.jsp","/announce.jsp", "/about-us.jsp", "/404.jsp", "/input-otp.jsp");

        // Nếu URL nằm trong danh sách bỏ qua, không thực hiện lọc
        boolean isExcluded = excludedUrls.stream().anyMatch(path::startsWith);
        if (isExcluded) {
            chain.doFilter(request, response);  // Bỏ qua authen cho các URL này
            return;
        }

        // Lấy thông tin từ cookie
        String userId = CookieUtil.getCookie(httpRequest, "id");
        String email = CookieUtil.getCookie(httpRequest, "email");

        // Kiểm tra xem user đã đăng nhập (dựa trên cookie 'id' và 'email')
        if (userId != null && email != null) {
            // Người dùng đã đăng nhập, tiếp tục request
            chain.doFilter(request, response);
        } else {
            // Chưa đăng nhập, chuyển hướng về trang đăng nhập
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    /**
     * <p>
     * Called by the web container to indicate to a filter that it is being taken out of service.
     * </p>
     *
     * <p>
     * This method is only called once all threads within the filter's doFilter method have exited or after a timeout period
     * has passed. After the web container calls this method, it will not call the doFilter method again on this instance of
     * the filter.
     * </p>
     *
     * <p>
     * This method gives the filter an opportunity to clean up any resources that are being held (for example, memory, file
     * handles, threads) and make sure that any persistent state is synchronized with the filter's current state in memory.
     * </p>
     *
     * @implSpec The default implementation takes no action.
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }

}
