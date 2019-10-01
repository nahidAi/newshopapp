package button.prg.com.test;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.melnykov.fab.FloatingActionButton;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

public class Show extends BaseActivity implements ObservableScrollViewCallbacks {

	private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

	private ImageView mImageView;
	private View mOverlayView;
	private ObservableScrollView mScrollView;
	private TextView mTitleView;
	private FloatingActionButton mFab;
	private int mActionBarSize;
	private int mFlexibleSpaceShowFabOffset;
	private int mFlexibleSpaceImageHeight;
	private int mFabMargin;
	private boolean mFabIsShown;
	public static int location;
	public static String title;
	public static boolean isFree;
	public static String body;
	public static int id;
	public int value;
	Database db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		db=new Database(Show.this);

		mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
		mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
		mActionBarSize = getActionBarSize();
		TextView text = (TextView) findViewById( R.id.body_main );

		mImageView = (ImageView) findViewById(R.id.image_show);
		mOverlayView = findViewById(R.id.overlay);
		mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
		mScrollView.setScrollViewCallbacks(this);
		mTitleView = (TextView) findViewById(R.id.title);
		//mTitleView.setText(title);
		mImageView.setImageResource( location );
		text.setText( Html.fromHtml(body) );
		int size=db.returnsize();
		text.setTextSize( size );


		//setTitle(title);
		mFab = (FloatingActionButton) findViewById(R.id.fab);

		value=db.fav_value( id );
		if (value==0)
		{
			mFab.setColorFilter( Color.argb( 255,255,0,17 ) );

		}
		else
		{
			mFab.setColorFilter( Color.argb( 255,0,255,124 ) );

		}




		mFab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (value==0)
				{
					db.fav( 1,id );
					mFab.setColorFilter( Color.argb( 255,0,255,124 ) );
					String note=title + " به لیست علاقه مندی ها اضافه شد";
					Toast.makeText(Show.this,note,Toast.LENGTH_LONG).show();

				}
				else
				{
					db.fav( 0,id );
					mFab.setColorFilter( Color.argb( 255,255,0,17 ) );
					String note=title + "ا ز لیست علاقه مندی ها حذف شد ";
					Toast.makeText( Show.this, note, Toast .LENGTH_LONG ).show();

				}
				value=db.fav_value( id );
			}
		});
		mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
		ViewHelper.setScaleX(mFab, 0);
		ViewHelper.setScaleY(mFab, 0);

		ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
			@Override
			public void run() {
				//mScrollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);

				// If you'd like to start from scrollY == 0, don't write like this:
				//mScrollView.scrollTo(0, 0);
				// The initial scrollY is 0, so it won't invoke onScrollChanged().
				// To do this, use the following:
				onScrollChanged(0, false, false);

				// You can also achieve it with the following codes.
				// This causes scroll change from 1 to 0.
				//mScrollView.scrollTo(0, 1);
				//mScrollView.scrollTo(0, 0);
			}
		});
	}

	@Override
	public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
		// Translate overlay and image
		float flexibleRange = mFlexibleSpaceImageHeight - mActionBarSize;
		int minOverlayTransitionY = mActionBarSize - mOverlayView.getHeight();
		ViewHelper.setTranslationY(mOverlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
		ViewHelper.setTranslationY(mImageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

		// Change alpha of overlay
		ViewHelper.setAlpha(mOverlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

		// Scale title text
		float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
		ViewHelper.setPivotX(mTitleView, 0);
		ViewHelper.setPivotY(mTitleView, 0);
		ViewHelper.setScaleX(mTitleView, scale);
		ViewHelper.setScaleY(mTitleView, scale);

		// Translate title text
		int maxTitleTranslationY = (int) (mFlexibleSpaceImageHeight - mTitleView.getHeight() * scale);
		int titleTranslationY = maxTitleTranslationY - scrollY;
		ViewHelper.setTranslationY(mTitleView, titleTranslationY);

		// Translate FAB
		int maxFabTranslationY = mFlexibleSpaceImageHeight - mFab.getHeight() / 2;
		float fabTranslationY = ScrollUtils.getFloat(
				-scrollY + mFlexibleSpaceImageHeight - mFab.getHeight() / 2,
				mActionBarSize - mFab.getHeight() / 2,
				maxFabTranslationY);
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			// On pre-honeycomb, ViewHelper.setTranslationX/Y does not set margin,
			// which causes FAB's OnClickListener not working.
			FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFab.getLayoutParams();
			lp.leftMargin = mOverlayView.getWidth() - mFabMargin - mFab.getWidth();
			lp.topMargin = (int) fabTranslationY;
			mFab.requestLayout();
		} else {
			ViewHelper.setTranslationX(mFab, mOverlayView.getWidth() - mFabMargin - mFab.getWidth());
			ViewHelper.setTranslationY(mFab, fabTranslationY);
		}

		// Show/hide FAB
		if (fabTranslationY < mFlexibleSpaceShowFabOffset) {
			hideFab();
		} else {
			showFab();
		}
	}

	@Override
	public void onDownMotionEvent() {
	}

	@Override
	public void onUpOrCancelMotionEvent(ScrollState scrollState) {
	}

	private void showFab() {
		if (!mFabIsShown) {
			ViewPropertyAnimator.animate(mFab).cancel();
			ViewPropertyAnimator.animate(mFab).scaleX(1).scaleY(1).setDuration(200).start();
			mFabIsShown = true;
		}
	}

	private void hideFab() {
		if (mFabIsShown) {
			ViewPropertyAnimator.animate(mFab).cancel();
			ViewPropertyAnimator.animate(mFab).scaleX(0).scaleY(0).setDuration(200).start();
			mFabIsShown = false;
		}
	}
}