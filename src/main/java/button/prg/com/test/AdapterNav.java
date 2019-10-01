package button.prg.com.test;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.Collections;
import java.util.List;

public class AdapterNav extends RecyclerView.Adapter<AdapterNav.ViewHolder>{
	private LayoutInflater inflater;
	List<InfoNav> data = Collections.emptyList();
	private Context context;

	public AdapterNav (Context context, List<InfoNav> data) {
		inflater = LayoutInflater.from( context );
		this.data = data;
		this.context = context;

	}

	@Override
	public ViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
		View view = inflater.inflate( R.layout.custom_row, parent, false );
		return new ViewHolder( view );
	}

	@Override
	//تعداد سطرهارو در این متد مشخص میکنیم.(متد محدود کننده)
	public void onBindViewHolder (ViewHolder holder, int position) {
		InfoNav cur = data.get( position );
		holder.title.setText( cur.title );
		holder.image.setImageResource( cur.iconId);
		Typeface typeface = Typeface.createFromAsset( context.getAssets(), "zienab.ttf" );
		holder.title.setTypeface( typeface );
	}

	@Override
	public int getItemCount () {
		return data.size();
	}

	// برای اضافه کردن حالت کلیک در ایتم های رسایکلرویو بدنبال کلاس زیر ایمپلنت میکنیم از آنکلیک لیسنرو متدش رو ایجا میکنیم .
	public class ViewHolder extends RecyclerView.ViewHolder{
		TextView title;
		ImageView image;
		//private Object findViewBy;
		MaterialRippleLayout materialRippleLayout;


		public ViewHolder (View itemView) {
			super( itemView );
			title = (TextView) itemView.findViewById( R.id.List_Nav_title );
			image = (ImageView) itemView.findViewById( R.id.List_Nav_image );
			materialRippleLayout = (MaterialRippleLayout) itemView.findViewById( R.id.ripple_re );
			materialRippleLayout.setOnClickListener( new View.OnClickListener(){
				@Override
				public void onClick (View view) {
					int position =getAdapterPosition();

					switch (position) {

						case 0:
							context.startActivity( new Intent( context, search.class ) );
							break;

						case 1:
							break;
					}
					if (getAdapterPosition()==1)
					{
						Intent intent=new Intent( context,Favorite.class );
						context.startActivity( intent );
					}
					if (getAdapterPosition()==2)
					{
						Intent intent=new Intent( context,Setting.class );
						context.startActivity( intent );
					}
					if (getAdapterPosition()==3)
					{
						final NiftyDialogBuilder dialogBuilder;
						dialogBuilder = NiftyDialogBuilder.getInstance(context);



						dialogBuilder
								.withTitle("منابع")
								.withMessage("معتبرترین کتاب ها و سایتهای آموزش جذب ثروت")
								.withTitleColor("#FFFFFF")
								.withDividerColor("#FFFFFF")
								.withDialogColor("#AB47BC")
								.withEffect(Effectstype.Fliph)
								.withDuration(500)
								.withButton1Text("خب")
								.setButton1Click(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										dialogBuilder.cancel();
									}
								})
								.show();
					}
					if (getAdapterPosition()==4)
					{
						Intent intent = new Intent(Intent.ACTION_EDIT);
						intent.setData( Uri.parse("bazaar://details?id=" + "com.estrongs.android.pop"));
						intent.setPackage("com.farsitel.bazaar");
						context.startActivity(intent);
					}
					if (getAdapterPosition()==5)
					{
						System.exit( 0 );
					}
				}
			} );


		}


	}
}










