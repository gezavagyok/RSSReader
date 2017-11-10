package tek.gezacsorba.rssreader.architecture;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import tek.gezacsorba.rssreader.BuildConfig;
import tek.gezacsorba.rssreader.R;

public abstract class BaseActivity extends AppCompatActivity implements Contract.View {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        injectDependencies();
        initView();
    }

    protected abstract void injectDependencies();

    protected abstract @LayoutRes int getLayoutId();

    protected abstract void initView();

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable throwable) {
        if(BuildConfig.DEBUG) {
            Snackbar.make(getWindow().getDecorView(), throwable.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(getWindow().getDecorView(), getString(R.string.error_message), Snackbar.LENGTH_SHORT).show();
        }
    }
}