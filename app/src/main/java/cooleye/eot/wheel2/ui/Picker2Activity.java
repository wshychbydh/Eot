package cooleye.eot.wheel2.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import cooleye.eot.R;
import cooleye.eot.wheel2.bean.Pickers;
import cooleye.eot.wheel2.views.PickerScrollView;

/**
 * 主界面
 * 
 * @author zengtao 2015年5月20日 下午7:36:03
 *
 */
public class Picker2Activity extends Activity {

	private Button bt_scrollchoose; // 滚动选择器按钮
	private PickerScrollView pickerscrlllview; // 滚动选择器
	private List<Pickers> list; // 滚动选择器数据
	private String[] id;
	private String[] name;

	private Button bt_yes; // 确定按钮
	private RelativeLayout picker_rel; // 选择器布局

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picker2);

		initView();
		initLinstener();
		initData();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		bt_scrollchoose = (Button) findViewById(R.id.bt_scrollchoose);
		picker_rel = (RelativeLayout) findViewById(R.id.picker_rel);
		pickerscrlllview = (PickerScrollView) findViewById(R.id.pickerscrlllview);
		bt_yes = (Button) findViewById(R.id.picker_yes);
	}

	/**
	 * 设置监听事件
	 */
	private void initLinstener() {
		bt_scrollchoose.setOnClickListener(onClickListener);
		pickerscrlllview.setOnSelectListener(pickerListener);
		bt_yes.setOnClickListener(onClickListener);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		list = new ArrayList<Pickers>();
		id = new String[] { "1", "2", "3", "4", "5", "6" };
		name = new String[] { "中国银行", "农业银行", "招商银行", "工商银行", "建设银行", "民生银行" };
		for (int i = 0; i < name.length; i++) {
			list.add(new Pickers(name[i], id[i]));
		}
		// 设置数据，默认选择第一条
		pickerscrlllview.setData(list);
		pickerscrlllview.setSelected(0);
	}

	// 滚动选择器选中事件
	PickerScrollView.onSelectListener pickerListener = new PickerScrollView.onSelectListener() {

		@Override
		public void onSelect(Pickers pickers) {
			System.out.println("选择：" + pickers.getShowId() + "--银行："
					+ pickers.getShowConetnt());
		}
	};

	// 点击监听事件
	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == bt_scrollchoose) {
				picker_rel.setVisibility(View.VISIBLE);
			} else if (v == bt_yes) {
				picker_rel.setVisibility(View.GONE);
			}
		}
	};
}
