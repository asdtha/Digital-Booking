package grupo4.backend.util;

import javax.servlet.http.HttpServletRequest;

public class GetUrlUtil {

    public static String getSiteURL (HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
