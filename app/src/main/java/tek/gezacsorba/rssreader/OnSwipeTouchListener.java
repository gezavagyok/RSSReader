package tek.gezacsorba.rssreader;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import tek.gezacsorba.rssreader.feed.FeedContract;
import tek.gezacsorba.rssreader.feed.FeedPresenter;

public class OnSwipeTouchListener implements OnTouchListener {

    private final GestureDetector gestureDetector;
    private final FeedContract.Presenter presenter;
    WeakReference<Context> context;

    public OnSwipeTouchListener(Context ctx, FeedContract.Presenter presenter) {
        gestureDetector = new GestureDetector(ctx, new GestureListener());
        this.presenter = presenter;
        context = new WeakReference<>(ctx);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener implements GestureDetector.OnGestureListener {

        private static final int SWIPE_THRESHOLD = -10;
        private static final int SWIPE_VELOCITY_THRESHOLD = -10;


        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            onClick();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 30) {
                            onSwipeRight();
                        } else if (diffX < -30) {
                            onSwipeLeft();
                        } else {
                            onClick();
                        }
                    }
                    result = true;
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 30) {
                        onSwipeBottom();
                    } else if (diffY < -30){
                        onSwipeTop();
                    } else {
                        onClick();
                    }
                    result = true;
                } else {
                    onClick();
                }
            } catch (
                    Exception exception)

            {
                exception.printStackTrace();
            }
            return result;
        }

    }

    public void onClick() {
        presenter.loadDetails(context.get());
    }

    public void onSwipeRight() {
        presenter.loadPreviousFeed();
    }

    public void onSwipeLeft() {
        presenter.loadNextFeed();
    }

    public void onSwipeTop() {
        presenter.loadPreviousFeed();
    }

    public void onSwipeBottom() {
        presenter.loadNextFeed();
    }
}