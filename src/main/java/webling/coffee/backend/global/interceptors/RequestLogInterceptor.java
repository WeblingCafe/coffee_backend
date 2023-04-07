package webling.coffee.backend.global.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import webling.coffee.backend.global.utils.JsonUtil;

import java.util.Enumeration;

import static webling.coffee.backend.global.constant.FileFormat.*;

@Slf4j
@Configuration
public class RequestLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (isNotStatic(request)) {

            if (log.isInfoEnabled()) {
                StringBuilder buffer = new StringBuilder();
                buffer.append("\n\n\n--------------------------------------------------\n").append("Request Information\n")
                        .append("--------------------------------------------------\n")
                        .append("Request URL : " + request.getRequestURL().toString() + "\n").append("Method : " + request.getMethod() + "\n")
                        .append("HTTP Status : " + response.getStatus() + "\n").append("Session ID : " + request.getSession().getId() + "\n")
                        .append("\n\nHeaders : ★★★★★★★★★★★★★★★★★★★★★★\n");

                Enumeration<String> headerNames = request.getHeaderNames();
                while (headerNames.hasMoreElements()) {
                    String headerName = headerNames.nextElement();
                    String headerValue = request.getHeader(headerName);
                    buffer.append(headerName).append(" : ").append(headerValue).append("\n");
                }

                if (!isMultipart(request)) {
                    buffer.append("\n\nParameters : ★★★★★★★★★★★★★★★★★★★★★★\n");

                    Enumeration<String> parameterNames = request.getParameterNames();

                    while (parameterNames.hasMoreElements()) {

                        String paramName = parameterNames.nextElement();
                        String[] paramValues = request.getParameterValues(paramName);

                        if (paramName.equals("password")) {
                            buffer.append(paramName).append(" : ").append("************").append("\n");
                            continue;
                        }

                        buffer.append(paramName).append(" : ").append(JsonUtil.marshallingJson(paramValues)).append("\n");
                    }
                }
                buffer.append("--------------------------------------------------\n");
                log.info(buffer.toString());
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    private boolean isMultipart (final HttpServletRequest request) {
        return request.getContentType() != null && request.getContentType().startsWith(MediaType.MULTIPART_FORM_DATA_VALUE);
    }

    private boolean isNotStatic(final HttpServletRequest request) {

        return !request.getRequestURL().toString().contains(IMG) &&
                !request.getRequestURL().toString().contains(CSS) &&
                !request.getRequestURL().toString().contains(JS) &&
                !request.getRequestURL().toString().contains(HTML);
    }

}
