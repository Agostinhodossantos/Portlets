package mail.newsletter.com.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String args[]) {
        List<String> categories = new ArrayList<>();
        categories.add("Hr");
        categories.add("Hr");
        categories.add("Hr");
        categories.add("Hr");


        String n = categories.toString();
        System.out.println(n.replace("[", "").replace("]", ""));

    }

}
