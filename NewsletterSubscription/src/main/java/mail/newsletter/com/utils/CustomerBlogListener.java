package mail.newsletter.com.utils;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.persistence.UserGroupUtil;

import java.util.List;
import static mail.newsletter.com.utils.EmailUtils.*;

public class CustomerBlogListener extends BaseModelListener<BlogsEntry>{
  private static Log _log = LogFactoryUtil.getLog(CustomerBlogListener.class);

  @Override
  public void onAfterCreate(BlogsEntry model) throws ModelListenerException {
    super.onAfterCreate(model);
    _log.info("Model create...");

//    _log.info(model.getContent());
//    _log.info(model.getCoverImageURL());
//    _log.info(model.getUrlTitle());
      _log.info("Create: "+model.getStatus());
//    _log.info(model);
  }

  @Override
  public void onAfterUpdate(BlogsEntry originalModel, BlogsEntry model) throws ModelListenerException {
    super.onAfterUpdate(originalModel, model);
    _log.info("Update: "+model.getStatus());


    if (model.getStatus() == 1) {
      long id = model.getUserId();
      try {
        User user = UserLocalServiceUtil.getUser(id);
        getNotifyAdmin(user);

      } catch (PortalException e) {
        throw new RuntimeException(e);
      }
    }

    if (model.getStatus() == 0) {
      getNotifyUsers(model);
    }
  }
}
