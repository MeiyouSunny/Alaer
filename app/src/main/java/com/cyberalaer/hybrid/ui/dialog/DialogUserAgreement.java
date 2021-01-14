package com.cyberalaer.hybrid.ui.dialog;

import android.os.Process;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alaer.lib.api.AppConfig;
import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseDialogHolder;
import com.cyberalaer.hybrid.databinding.DialogUserAgreementBinding;
import com.cyberalaer.hybrid.ui.webpage.WebPageActivity;

/**
 * 用户协议Dialog
 */
public class DialogUserAgreement extends BaseDialogHolder<DialogUserAgreementBinding> {

    public DialogUserAgreement() {
        super(R.layout.dialog_user_agreement);
    }

    @Override
    public void onViewCreated(View view) {
        super.onViewCreated(view);
        bindRoot.webView.loadData(getContext().getString(R.string.user_agreement_html), "", "UTF-8");
        bindRoot.webView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    public void click(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                Process.killProcess(Process.myPid());
                break;
            case R.id.confirm:
                dismiss();
                break;
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith("agreement://")) {
                WebPageActivity.start(getContext(), AppConfig.USER_AGREEMENT, R.string.user_agreement);
                return true;
            }
            return false;
        }
    }

}
