package tek.gezacsorba.rssreader.di.login;

import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;
import tek.gezacsorba.rssreader.LoginContract;
import tek.gezacsorba.rssreader.LoginPresenter;

/**
 * Created by geza on 11/9/17.
 */
@Module
public class LoginModule {

    LoginContract.LoginView view;

    public LoginModule(LoginContract.LoginView view) {
        this.view = view;
    }

    @Provides
    FirebaseAuth provideAuth() {
        return FirebaseAuth.getInstance();
    }

    @Provides
    CallbackManager provideCallbackManager(){
        return CallbackManager.Factory.create();
    }

    @Provides
    LoginContract.LoginPresenter provideLoginPresenter(FirebaseAuth auth, CallbackManager callbackManager){
        return new LoginPresenter(auth,callbackManager,view);
    }
}
