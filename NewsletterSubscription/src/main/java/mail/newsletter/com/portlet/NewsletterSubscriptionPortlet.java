package mail.newsletter.com.portlet;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import mail.newsletter.com.constants.NewsletterSubscriptionPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.mail.internet.AddressException;
import javax.naming.Context;
import javax.portlet.*;

import org.osgi.service.component.annotations.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static mail.newsletter.com.constants.NewsletterSubscriptionPortletKeys.FROM_EMAIL;
import static mail.newsletter.com.utils.ContentFile.writeContent;
import static mail.newsletter.com.utils.EmailUtils.*;
import static mail.newsletter.com.utils.UserSubscription.*;

/**
 * @author Agostinho
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.sample",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=NewsletterSubscription",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + NewsletterSubscriptionPortletKeys.NEWSLETTERSUBSCRIPTION,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class NewsletterSubscriptionPortlet extends MVCPortlet {


	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException{

		List<AssetCategory> categoryList = AssetCategoryLocalServiceUtil.getCategories();
		String[] names = new String[categoryList.size()];
		int i = 0;
		for (AssetCategory c : categoryList) {
			names[i] = c.getName();
			i++;
		}
		renderRequest.setAttribute("categoryList", categoryList);
		renderRequest.setAttribute("names", names);
		super.render(renderRequest, renderResponse);
	}

	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException {


		String email = actionRequest.getParameter("email");
		String body = actionRequest.getParameter("body");
		String subject = actionRequest.getParameter("subject");
		StringBuilder selectedCategory = new StringBuilder();

		List<String> categories = new ArrayList<>();


		for (String id : actionRequest.getParameterValues("name")) {
			categories.add(id);
		}

		String strCategory = categories.toString();
		strCategory = strCategory.replace("[", "").replace("]", "");
		body = body+" "+strCategory;

		if(email == null) {
			SessionErrors.add(actionRequest, "error-key");
			return;
		}

//		List<String> emails = getUsers();
//
//		if(emails.contains(email)) {
//			SessionErrors.add(actionRequest, "error-email");
//			return;
//		}

		if (isValidEmailAddress(email)) {
			subscribeUser(email);

			String from = FROM_EMAIL;
			String pageLink = "http://www.ine.gov.mz";
			String unsubscribeLink = getHostLink(true)+"/unsubscribe?email="+email;
			String btnName = "Voltar no site";

			try {
				sendMail(email,from, createMailContent(subject,body, pageLink, unsubscribeLink,btnName) , subject);
			} catch (AddressException e) {
				SessionErrors.add(actionRequest, "error-key");

				throw new RuntimeException(e);
			}

			String finalEmail = email;
			categories.forEach(category ->{
				setUserCategories(finalEmail, category);
			});
			SessionMessages.add(actionRequest, "key");
			actionResponse.sendRedirect("/subscrito-com-sucesso");
		} else {
			SessionErrors.add(actionRequest, "error-key");
		}

		actionResponse.sendRedirect("/subscrito-com-sucesso");

		//actionResponse.setRenderParameter("email", email);
		super.processAction(actionRequest, actionResponse);
	}

}