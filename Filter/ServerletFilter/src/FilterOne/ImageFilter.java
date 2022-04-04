package FilterOne;

import java.io.File;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class ImageFilter
 */
@WebFilter(urlPatterns = { "*.png", "*.jpg", "*.gif" }, initParams = {
		@WebInitParam(name = "notFoundImage", value = "/images/BangCam.png") })
public class ImageFilter implements Filter {
	private String notFoundImage;
    /**
     * Default constructor. 
     */
    public ImageFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		HttpServletRequest req = (HttpServletRequest) request;

		// ==> /images/path/my-image.png
		// ==> /path1/path2/image.pngs
		String servletPath = req.getServletPath();

		// �?ư�?ng dẫn tuyệt đối của thư mục gốc của WebApp (WebContent).
		String realRootPath = request.getServletContext().getRealPath("");

		// �?ư�?ng dẫn tuyệt đối tới file ảnh.
		String imageRealPath = realRootPath + servletPath;

		System.out.println("imageRealPath = " + imageRealPath);

		File file = new File(imageRealPath);

		// Kiểm tra xem ảnh có tồn tại không.
		if (file.exists()) {

			// Cho phép request được đi tiếp. (Vượt qua Filter này).
			// (�?ể đi tiếp tới file ảnh yêu cầu).
			chain.doFilter(request, response);

		} else if (!servletPath.equals(this.notFoundImage)) {

			// Redirect (Chuyển hướng) tới file ảnh 'image not found'.
			HttpServletResponse resp = (HttpServletResponse) response;

			// ==> /ServletFilterTutorial + /images/image-not-found.png
			resp.sendRedirect(req.getContextPath() + this.notFoundImage);

		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		notFoundImage = fConfig.getInitParameter("notFoundImage");
	}

}
