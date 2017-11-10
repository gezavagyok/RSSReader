package tek.gezacsorba.rssreader.feed;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import tek.gezacsorba.rssreader.R;

/**
 * Created by geza on 11/9/17.
 */

public class UserInfoPlugin implements MenuPlugin {

    View root;

    @BindView(R.id.iv_profile_image)
    ProfilePictureView profileImageView;

    @BindView(R.id.tv_user_name)
    TextView userNameView;

    @BindView(R.id.tv_user_email)
    TextView userEmailView;

    @Override
    public void setRootView(View root) {
        this.root = root;
        ButterKnife.bind(this, root);
    }

    @Override
    public void init(){
        Profile profile = Profile.getCurrentProfile();

        profileImageView.setCropped(true);
        profileImageView.setProfileId(profile.getId());

        userNameView.setText(profile.getName());
        userEmailView.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
    }


}
