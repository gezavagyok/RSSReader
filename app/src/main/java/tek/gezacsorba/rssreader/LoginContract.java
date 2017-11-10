package tek.gezacsorba.rssreader;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.facebook.login.widget.LoginButton;

import tek.gezacsorba.rssreader.architecture.Contract;

/**
 * Created by geza on 11/9/17.
 */

public interface LoginContract {

    interface LoginView extends Contract.View {

    }

    interface LoginPresenter {
        void handleAuthResult(int requestCode, int resultCode, Intent data);
        void setupFacebookLogin(LoginButton button);
        void onCreate(AppCompatActivity context);
    }
}
