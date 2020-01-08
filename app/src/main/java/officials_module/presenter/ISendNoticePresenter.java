package officials_module.presenter;

import android.content.Context;

import com.code_base_update.beans.HostelNoticeBean;
import com.code_base_update.presenters.IBasePresenter;

import officials_module.view.ISendNoticeView;

public interface ISendNoticePresenter extends IBasePresenter<ISendNoticeView> {

    void sendNotice(Context context, HostelNoticeBean noticeBean);

}
