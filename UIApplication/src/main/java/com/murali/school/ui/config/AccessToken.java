package com.murali.school.ui.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

public class AccessToken {

	private static String accessTokenUri="http://localhost:9191/oauth/token";

	private static String clientId="mobile";

	private static String secret="pin";

	public static OAuth2AccessToken getAccessToken() {

		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(getPasswordResourceDetails(),
				new DefaultOAuth2ClientContext());
		return oAuth2RestTemplate.getAccessToken();
	}

	public static ResourceOwnerPasswordResourceDetails getPasswordResourceDetails() {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Object password = SecurityContextHolder.getContext().getAuthentication().getCredentials();

		ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
		resourceDetails.setClientAuthenticationScheme(AuthenticationScheme.header);
		resourceDetails.setAccessTokenUri(accessTokenUri);
		resourceDetails.setScope(Arrays.asList("READ", "WRITE"));
		resourceDetails.setClientId(clientId);
		resourceDetails.setClientSecret(secret);
		resourceDetails.setUsername(username);
		resourceDetails.setPassword(password.toString());

		return resourceDetails;
	}

}
