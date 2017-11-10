package tek.gezacsorba.rssreader.di.login;

import dagger.Component;
import tek.gezacsorba.rssreader.LoginActivity;

/**
 * Created by geza on 11/9/17.
 */
@Component(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}
