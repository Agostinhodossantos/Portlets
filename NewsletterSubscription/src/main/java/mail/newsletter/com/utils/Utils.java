package mail.newsletter.com.utils;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Long> getUserGroupsGroup(User user) {
        List<Long> groups = new ArrayList<>();
        List<UserGroup> userGroupList = user.getUserGroups();

        userGroupList.forEach( group -> {
            groups.add(group.getUserGroupId());
        });
        return groups;
    }

    public static boolean isSameGroup(List<Long> publisher , List<Long> reviewer) {
        Boolean isParent = false;
        for (Long currentPublisher: publisher) {
            if (reviewer.contains(currentPublisher)) {
                isParent = true;
                break;
            }
        }
        return isParent;
    }

}
