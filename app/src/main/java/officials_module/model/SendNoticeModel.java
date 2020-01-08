package officials_module.model;

import android.content.Context;
import android.net.Uri;

import com.code_base_update.DatabaseManager;
import com.code_base_update.ImageHelper;
import com.code_base_update.beans.HostelNoticeBean;
import com.code_base_update.interfaces.ImageUploadCallback;
import com.code_base_update.interfaces.SuccessCallback;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import id.zelory.compressor.Compressor;
import officials_module.presenter.ISendNoticePresenter;
import officials_module.view.ISendNoticeView;

public class SendNoticeModel implements ISendNoticePresenter {

    private ISendNoticeView mView;

    @Override
    public void sendNotice(Context context, HostelNoticeBean notice) {
        if (mView.getImage() == null)
            sendNoticeToDatabase(context, notice);
        else
            saveImageToFirebase(context, notice, mView.getImage());
    }


    private void sendNoticeToDatabase(Context context, HostelNoticeBean notice) {
        new DatabaseManager(context).saveNotice(notice,new SuccessCallback(){
            @Override
            public void onInitiated() {
                mView.sendingInitiated();
            }

            @Override
            public void onSuccess() {
                mView.sentSuccessfully();
            }

            @Override
            public void onFailure(String msg) {
                mView.sendingFailed(msg);
            }
        });

    }

    @Override
    public void attachView(ISendNoticeView view) {
        this.mView = view;
    }

    @Override
    public void detachView() {

    }

    private void saveImageToFirebase(final Context context, final HostelNoticeBean notice, Uri resultUri) {
        try {
            File currentFile = new Compressor(context).compressToFile(new File(resultUri.getPath()));
            Uri compressedUri = Uri.fromFile(currentFile);

            StorageReference mProfileReference = FirebaseStorage.getInstance().getReference("notice/" + notice.getNoticeId());
            ImageHelper.saveImageToServer(compressedUri, mProfileReference, new ImageUploadCallback() {
                @Override
                public void initiated() {
                    mView.uploadingImage();
                }

                @Override
                public void success(Uri Url) {
                    notice.setImageUrl(Url.toString());
                    sendNoticeToDatabase(context,notice);
                }

                @Override
                public void failed(String message) {
                    mView.sendingFailed(message);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
