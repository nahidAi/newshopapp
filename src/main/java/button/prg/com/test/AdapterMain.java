package button.prg.com.test;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//1-مشتق کردن کلاس از رسایکلر ویو
//4-ارتباط دادن و شناسوندن کلاس ویوهولدر در کلاس اصل      ی
//5-ایجاد 3متد مورد نیاز کلاس آداپتر
public class AdapterMain extends RecyclerView.Adapter<AdapterMain.ViewHolder> {
    //7-برای اینکه از کانتکست و لیست در خارج از محدوده کانسراکتور بتونیم استفاده کنیم باید
    //یک نمونه ازشون ایجاد کنیم تا بتونیم در کل سطح کلاس ازشون استفاده کنیم.
    private Context context;
    private List<InfoNav> data;
    MainActivity mActivity;
    ProgressDialog progressDialog;


    //8-ایجاد یک لیوت اینفیلیتر برای اتصال دادن این دو به کاستوم روو
    private LayoutInflater inflater;

    //6-ایجاد یک کانسراکتور جدیدبرای اینکه لازمه یک لیست و یک کانتکست اطلاعاتشو بگیره
    public AdapterMain(Context context, List<InfoNav> data) {
        //9-------------

        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //10-ایجاد یک ویو برای برقرار کردن عما اتصال به لیوت کاستوم
        View view = inflater.inflate(R.layout.custom_row_main, parent, false);
        //11-ساخت یک نمونه از کلاس پایینی همون ویو هولدر که بخواهیم اونو پاسش کنیم اینجا
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InfoNav cur = data.get(position);
        holder.textView.setText(cur.title);
        holder.circleImageView.setImageResource(cur.iconId);
        //ایجاد تایپ فیس برای تغییر فونت
        Typeface font = Typeface.createFromAsset(context.getAssets(), "zienab.ttf");
        holder.textView.setTypeface(font);

    }

    @Override
    public int getItemCount() {
        //14- اگر ریترن مقدار 0 را برگردونه لیست خالی نشون داده میشه باید به روش زیر تغییرش بدیم
        return data.size();
    }


    //2-ایجاد یک کلاس داخلی بنام ویو هولدر و مشتق کردن آن از ریسایکلر ویو
    class ViewHolder extends RecyclerView.ViewHolder {
        //12-اینجا باید دو تا مقدار وارد کنیم یه استرینگ داریم و یه عکس
        private TextView textView;
        private CircleImageView circleImageView;
        private MaterialRippleLayout m;

        //3-تعریف یک کانسراکتور  چون لازمه اطلاعات یه لیست و یک کانتکست را بعنوان ورودی بگیره
        public ViewHolder(View itemView) {
            super(itemView);
            //13-عمل مچ کردن باید انجام بشه
            textView = (TextView) itemView.findViewById(R.id.title_main);
            circleImageView = (CircleImageView) itemView.findViewById(R.id.pic_main);
            m = (MaterialRippleLayout) itemView.findViewById(R.id.ripple_re);
            m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InfoNav cur = new InfoNav();
                    cur = data.get(getAdapterPosition());

                    if (cur.isFree) {
                        Show.title = cur.title;
                        Show.location = cur.iconId;
                        Show.body = cur.body;
                        Show.id = getAdapterPosition() + 1;
                        Intent intent = new Intent(context, Show.class);
                        context.startActivity(intent);

                    } else {
                        MainActivity mainActivity = new MainActivity();
                        mainActivity.purchasePremiumAccant();


                       /* new MaterialStyledDialog.Builder(context)
                                .setDescription("برای مشاهده این بخش باید نسخه نهایی را خریداری کنید .درگاه پرداخت بازار باز شود؟")
                                .setPositiveText("بله")
                                .setHeaderColor(R.color.violet3)
                                .setIcon(R.drawable.lock_open)
                                .setCancelable(true)
                                .withIconAnimation(true)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {*/

                                       /* progressDialog = new ProgressDialog(context);
                                        progressDialog.setMessage("در حال دریافت اطلاعات از بازار لطفا منتظر بمانید...");
                                        progressDialog.setCancelable(false);
                                        progressDialog.show();*/
                       // mActivity.checkMyAccount();



                                   /* }
                                })
                                .setNegativeText("خیر")
                                .onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                    }
                                })
                                .show();*/

                    }


                }
            });


        }
    }


}
