package mx.androidtitlan.fragmentoverfragment.ui;

import java.util.ArrayList;
import java.util.List;

import mx.androidtitlan.fragmentoverfragment.BaseActivity;
import mx.androidtitlan.fragmentoverfragment.R;
import mx.androidtitlan.fragmentoverfragment.R.menu;
import mx.androidtitlan.fragmentoverfragment.adapter.SleevePagerViewAdapter;
import mx.androidtitlan.fragmentoverfragment.fragment.FirstSleeveImageFragment;
import mx.androidtitlan.fragmentoverfragment.fragment.MenuFragment;
import mx.androidtitlan.fragmentoverfragment.fragment.SecondSleeveImageFragment;
import mx.androidtitlan.fragmentoverfragment.fragment.SleeveFragment;
import mx.androidtitlan.fragmentoverfragment.fragment.ThirdSleeveImageFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.slidingmenu.lib.SlidingMenu;

public class HomeActivity extends BaseActivity {

	private Fragment mContent;
	ViewPager mViewPager;
	FragmentStatePagerAdapter mPagerAdapter;
	protected ListFragment mFrag;

	public HomeActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the Above View
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new SleeveFragment("", "home");

		// set the Above View
		setContentView(R.layout.content_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment()).commit();

		// customize the SlidingMenu
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);

		// setting up our ViewPager
		mPagerAdapter = new SleevePagerViewAdapter(this, createFragments());
		final ActionBar mActionBar = getSupportActionBar();
		mViewPager = (ViewPager) findViewById(R.id.view_pager);
		mViewPager.setAdapter(mPagerAdapter);

	}

	private List<Fragment> createFragments() {
		List<Fragment> list = new ArrayList<Fragment>();
		list.add(Fragment.instantiate(this,
				FirstSleeveImageFragment.class.getName()));
		list.add(Fragment.instantiate(this,
				SecondSleeveImageFragment.class.getName()));
		list.add(Fragment.instantiate(this,
				ThirdSleeveImageFragment.class.getName()));
		return list;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(Fragment fragment) {
		//TODO Remove all instances from last Fragment
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

}
