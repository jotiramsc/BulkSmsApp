package com.sms.auth;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
	private static final long serialVersionUID = -7858869558953243875L;

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException e) throws IOException, ServletException {

		final String expired = (String) httpServletRequest.getAttribute("expired");
		System.out.println(expired);
		if (expired != null) {
			// ApiResponse apiResponse = new ApiResponse(401, "Unauthorized. Token is
			// expired", false);
//			OutputStream outputStream = httpServletResponse.getOutputStream();
//			ObjectMapper mapper = new ObjectMapper();
//			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			mapper.writeValue(outputStream, "fdakjkkhfd");
//			outputStream.flush();
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Expired");
		} else {
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Login details");
		}
	}
}