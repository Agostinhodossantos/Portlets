package mail.newsletter.com.utils;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryModel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.UserGroupUtil;
import org.osgi.service.component.annotations.Component;

import java.util.ArrayList;
import java.util.List;

import static mail.newsletter.com.utils.EmailUtils.*;

@Component(
        immediate = true,
        service = ModelListener.class
)

public class CustomerFileListener extends BaseModelListener<DLFileEntry> {
    @Override
    public void onAfterCreate(DLFileEntry model) throws ModelListenerException {
        System.out.println("AssetEntry");

        long id = model.getUserId();
        try {
            User user = UserLocalServiceUtil.getUser(id);
            getNotifyAdmin(user);

        } catch (PortalException e) {
            throw new RuntimeException(e);
        }
        super.onAfterCreate(model);
    }

}
