package officials_module.view;

import android.net.Uri;

import com.code_base_update.view.IBaseView;

public interface ISendNoticeView extends IBaseView {

    Uri getImage();

    void sendingInitiated();

    void sendingFailed(String msg);

    void sentSuccessfully();

    void onErrorOccurred();

    void uploadingImage();
}
