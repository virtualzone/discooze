package net.weweave.discooze.backend.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResponseFilter implements Filter {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Value("${proxy.public-base-path}")
    private String publicBasePath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ResponseWrapper capturingResponseWrapper = new ResponseWrapper((HttpServletResponse) response);
        chain.doFilter(request, capturingResponseWrapper);
        if (modifyResponse(response.getContentType())) {
            String url = ((HttpServletRequest)request).getRequestURI();
            logger.info("Modifying reponse content for URL " + url);
            String content = capturingResponseWrapper.getCaptureAsString();
            content = content.replace("[PUBLIC_BASE_PATH]", this.publicBasePath);
            response.setContentLength(content.length());
            response.getWriter().write(content);
        } else {
            response.getOutputStream().write(capturingResponseWrapper.getCaptureAsBytes());
        }
    }

    @Override
    public void destroy() {
    }

    private boolean modifyResponse(String contentType) {
        if (contentType == null) {
            return true;
        }
        if (StringUtils.hasText(contentType)) {
            if (contentType.contains("text/html")) {
                return true;
            }
        }
        return false;
    }
}
