package cooleye.eot.wheel;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cooleye.eot.R;
import cooleye.eot.wheel.widget.OnWheelChangedListener;
import cooleye.eot.wheel.widget.OnWheelScrollListener;
import cooleye.eot.wheel.widget.WheelView;
import cooleye.eot.wheel.widget.adapters.AbstractWheelTextAdapter;
import cooleye.eot.wheel.widget.adapters.ArrayWheelAdapter;

public class CitiesActivity extends Activity {
	// Scrolling flag
	private boolean scrolling = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.cities_layout);

		final WheelView country = (WheelView) findViewById(R.id.country);
		country.setVisibleItems(3);
		country.setCyclic(true);
		country.setDrawShadows(false);
		country.setWheelBackground(R.drawable.canada);
		country.setBackgroundColor(getResources().getColor(R.color.white));
		country.setViewAdapter(new CountryAdapter(this));

		final String cities[][] = new String[][] {
				new String[] {"New York", "Washington", "Chicago", "Atlanta", "Orlando"},
				new String[] {"Ottawa", "Vancouver", "Toronto", "Windsor", "Montreal"},
				new String[] {"Kiev", "Dnipro", "Lviv", "Kharkiv"},
				new String[] {"Paris", "Bordeaux"},
		};

		final WheelView city = (WheelView) findViewById(R.id.city);
		city.setVisibleItems(5);
		city.setCyclic(true);

		country.addChangingListener(new OnWheelChangedListener() {
			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				if (!scrolling) {
					updateCities(city, cities, newValue);
				}
			}
		});

		country.addScrollingListener( new OnWheelScrollListener() {
			@Override
			public void onScrollingStarted(WheelView wheel) {
				scrolling = true;
			}
			@Override
			public void onScrollingFinished(WheelView wheel) {
				scrolling = false;
				updateCities(city, cities, country.getCurrentItem());
			}
		});

		country.setCurrentItem(1);
	}

	/**
	 * Updates the city wheel
	 */
	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter =
				new ArrayWheelAdapter<String>(this, cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(cities[index].length / 2);
	}

	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] =
				new String[] {"USA", "Canada", "Ukraine", "France"};
		// Countries flags
		private int flags[] =
				new int[] {R.drawable.usa, R.drawable.canada, R.drawable.ukraine, R.drawable.france};

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.country_layout, NO_RESOURCE);

			setItemTextResource(R.id.country_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			ImageView img = (ImageView) view.findViewById(R.id.flag);
			img.setImageResource(flags[index]);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}
}
