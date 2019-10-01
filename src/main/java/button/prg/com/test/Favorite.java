package button.prg.com.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class Favorite extends AppCompatActivity{

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_favorite );
		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.re_favorite);
		ImageView image = (ImageView) findViewById(R.id.image_favorite);
		image.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick (View view) {
				Intent intent = new Intent( Favorite.this, MainActivity.class );
				startActivity( intent );
			}
		} );
		Database db = new Database( Favorite.this );
		List<info_Data> data = db.favorite();
		AdapterMain adapter = new AdapterMain(Favorite.this, postdata(data));
		AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(adapter);
		ScaleInAnimationAdapter scaleInAnimationAdapter = new ScaleInAnimationAdapter( animationAdapter );
		scaleInAnimationAdapter.setDuration( 1000 );
		scaleInAnimationAdapter.setFirstOnly( false );
		recyclerView.setAdapter( scaleInAnimationAdapter );
		recyclerView.setLayoutManager( new LinearLayoutManager( Favorite.this ) );

	}
	
	private List<InfoNav> postdata (List<info_Data> db) {
		List<InfoNav> data = new ArrayList<>();
		for (int i = 0; i < db.size(); i++) {
			InfoNav cur = new InfoNav();
			cur.title = db.get( i ).getName();
			String uri = "@drawable/" + db.get( i ).getImage();
			switch (cur.iconId = getResources().getIdentifier(uri, null, getPackageName())) {
			}
			cur.body = db.get( i ).getBody();
			data.add( cur );

		}
		return data;

	}
	
}