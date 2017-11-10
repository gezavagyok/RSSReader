package tek.gezacsorba.rssreader;

import android.content.Intent;

import com.facebook.login.widget.LoginButton;

import javax.inject.Inject;

import butterknife.BindView;
import tek.gezacsorba.rssreader.architecture.BaseActivity;
import tek.gezacsorba.rssreader.di.login.DaggerLoginComponent;
import tek.gezacsorba.rssreader.di.login.LoginModule;

/**
 * Created by geza on 11/8/17.
 */

public class LoginActivity extends BaseActivity implements LoginContract.LoginView{

    @BindView(R.id.login_button)
    LoginButton loginButton;

    @Inject
    LoginContract.LoginPresenter presenter;

    @Override
    protected void injectDependencies() {
        DaggerLoginComponent.builder()
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        presenter.onCreate(this);
        presenter.setupFacebookLogin(loginButton);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.handleAuthResult(requestCode,resultCode,data);
    }


}
