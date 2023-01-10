package mail.newsletter.com.utils;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.*;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

public class GetUserRoles {

    static List<Role> getUserExplicitRoles(User user) throws SystemException, PortalException {
        List<Role> roles = new ArrayList<Role>();
        List<UserGroupRole> userGroupRoles = UserGroupRoleLocalServiceUtil.getUserGroupRoles(user.getUserId());
        for (UserGroupRole userGroupRole : userGroupRoles) {
            roles.add(userGroupRole.getRole());
        }
        return roles;
    }

    static List<Role> getUserGroupRolesOfUser(User user) throws SystemException, PortalException {
        List<Role> roles = new ArrayList<Role>();
        List<UserGroup> userGroupList = UserGroupLocalServiceUtil.getUserUserGroups(user.getUserId());
        List<UserGroupGroupRole> userGroupGroupRoles = new ArrayList<UserGroupGroupRole>();
        for (UserGroup userGroup : userGroupList) {
            userGroupGroupRoles.addAll(UserGroupGroupRoleLocalServiceUtil.getUserGroupGroupRoles(userGroup
                    .getUserGroupId()));
        }
        for (UserGroupGroupRole userGroupGroupRole : userGroupGroupRoles) {
            Role role = RoleLocalServiceUtil.getRole(userGroupGroupRole.getRoleId());
            roles.add(role);
        }
        return roles;
    }


}
