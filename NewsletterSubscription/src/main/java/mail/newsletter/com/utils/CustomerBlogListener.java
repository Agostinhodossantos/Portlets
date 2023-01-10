package mail.newsletter.com.utils;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;

import javax.mail.internet.AddressException;
import java.util.List;

import static mail.newsletter.com.constants.NewsletterSubscriptionPortletKeys.FROM_EMAIL;
import static mail.newsletter.com.constants.NewsletterSubscriptionPortletKeys.SEND_ALL;
import static mail.newsletter.com.utils.EmailUtils.*;
import static mail.newsletter.com.utils.UserSubscription.getHostLink;
import static mail.newsletter.com.utils.UserSubscription.getUsers;

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
      getNotifyAdmin();
    }

    if (model.getStatus() == 0) {
      getNotifyUsers(model);
    }
  }
}
