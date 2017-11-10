package tek.gezacsorba.rssreader.architecture;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by geza on 11/5/17.
 */

public interface Contract {

    interface Presenter {

        CompositeDisposable disposables = new CompositeDisposable();

        void onCreate();

        void onDestroy();

    }

    interface View {

        void showLoading();

        void hideLoading();

        void showError(Throwable throwable);
    }
}
