package button.prg.com.test;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragMain extends Fragment{




	public FragMain () {

		// Required empty public constructor
	}


	@Override
	public View onCreateView (LayoutInflater inflater, ViewGroup container,
	                          Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate( R.layout.fragment_frag_main, container, false );
		RecyclerView recyclerView = (RecyclerView) view.findViewById( R.id.main_re );
		Database db = new Database( getActivity() );
		List<info_Data> data=db.fetchmaindata();
		db.close();

		AdapterMain adapter = new AdapterMain( getActivity(), postdata(data) );

		FloatingActionButton fab = (FloatingActionButton) view.findViewById( R.id.fab_main );
		fab.setColorNormal(getResources().getColor(R.color.violet3));
		fab.attachToRecyclerView( recyclerView );
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View view) {
				final NiftyDialogBuilder dialogBuilder;
				dialogBuilder = NiftyDialogBuilder.getInstance(getActivity());



				dialogBuilder
						.withTitle("Gmail")
						.withMessage("na.mehr20@gmail.com")
						.withTitleColor("#FFFFFF")
						.withDividerColor("#FFFFFF")
						.withDialogColor("#AB47BC")
						.withEffect(Effectstype.Fliph)
						.withDuration(500)
						.withButton1Text("OK")
						.setButton1Click(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								dialogBuilder.cancel();
							}
						})
						.show();
			}
		});



		AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter( adapter );
		ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter( animationAdapter );
		//برای مشخص کردن زمان اجرای انیمیشن
		scaleInAnimationAdapter.setDuration(1000);
		//برای مشخص کردن ایمکه حالت انیمیشن فقط برای بار اول اجرا بشه یا هر زمان که اسکرول میشه این حالت رو داشته باشه
		scaleInAnimationAdapter.setFirstOnly( false );
		recyclerView.setAdapter( scaleInAnimationAdapter );
		recyclerView.setLayoutManager(new  LinearLayoutManager(getActivity()) );
		return view;
	}
	//17-این متد را ایجاد میکنیم برای پر کردن اطلاعات نیو آداپتر که بالا در شماره 16 ایجاد کردیم
	private List<InfoNav>postdata(List<info_Data> db){
		//18- یک اری لیست درست میکنیم
		List<InfoNav>data = new ArrayList<>(  );

		//20-ایجا فور برای اینکه اطلاعات رو برامون ادد کنه
		for (int i = 0;i<db.size() ;i++){
			InfoNav cur =new InfoNav();
			cur.title = db.get( i ).getName();
			String uri = "@drawable/"+db.get( i ).getImage();
			cur.iconId = getResources().getIdentifier( uri,null,getActivity().getPackageName() );
			cur.body=db.get( i ).getBody();
			cur.isFree = db.get(i).getIsFree();
			data.add( cur );

		}
		//21- و در اخر دیتا رو بعنوان لیست ارسال میکنیم
		return data;

	}


}
