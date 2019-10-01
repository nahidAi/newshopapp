package button.prg.com.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class search extends AppCompatActivity{
	private RecyclerView recyclerView;
	private AdapterMain adapter;
	private EditText text;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_search );
		recyclerView = (RecyclerView) findViewById( R.id.search_re );
		text = (EditText) findViewById( R.id.text_search );
		ImageView image = (ImageView) findViewById(R.id.back_search);
		image.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick (View view) {
				Intent intent = new Intent( search.this, MainActivity.class );
				startActivity( intent );

			}
		} );
		text.addTextChangedListener( new TextWatcher(){
			@Override
			public void beforeTextChanged (CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged (CharSequence charSequence, int i, int i1, int i2) {
				if (text.getText().toString().equals( "" )) {
					List<InfoNav> data = Collections.EMPTY_LIST;
					AdapterMain adapter = new AdapterMain( search.this, data );

					AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter( adapter );
					ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter( animationAdapter );
					scaleInAnimationAdapter.setDuration( 1000 );
					scaleInAnimationAdapter.setFirstOnly( false );
					recyclerView.setAdapter( scaleInAnimationAdapter );
					recyclerView.setLayoutManager( new LinearLayoutManager( search.this ) );

				} else {
					Database db = new Database( search.this );
					List<info_Data> data = db.search( text.getText().toString() );
					db.close();

					AdapterMain adapter = new AdapterMain( search.this, postdata( data ) );

					AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter( adapter );
					ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter( animationAdapter );
					scaleInAnimationAdapter.setDuration( 1000 );
					scaleInAnimationAdapter.setFirstOnly( false );
					recyclerView.setAdapter( scaleInAnimationAdapter );
					recyclerView.setLayoutManager( new LinearLayoutManager( search.this ) );
				}

			}

			@Override
			public void afterTextChanged (Editable editable) {

			}
		} );
	}

	private List<InfoNav> postdata (List<info_Data> db) {
		List<InfoNav> data = new ArrayList<>();
		for (int i = 0; i < db.size(); i++) {
			InfoNav cur = new InfoNav();
			cur.title = db.get( i ).getName();
			String uri = "@drawable/" + db.get( i ).getImage();
			int iconid = getResources().getIdentifier( uri, null, getPackageName() );
			cur.iconId = iconid;
			cur.body = db.get( i ).getBody();
			data.add( cur );

		}
		return data;

	}

}
