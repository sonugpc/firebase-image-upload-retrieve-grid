package big.in.imageupload;

/**
 * Created by sonug on 3/29/2018.
 */


        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.squareup.picasso.Picasso;

        import java.util.List;


public class Madapter extends RecyclerView.Adapter<Madapter.ImageViewHolder> {
private  Context mContext;
private List<ImgData> ud;

public Madapter(Context context, List<ImgData> ud1)
{
    mContext = context;
    ud = ud1;
}

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(mContext).inflate(R.layout.imgitm,parent,false);
        return new ImageViewHolder(v);
}

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

    ImgData upcurrent=ud.get(position);
    holder.name.setText(upcurrent.getName());
        Picasso.with(mContext)
                .load(upcurrent.getUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);




    }

    @Override
    public int getItemCount() {
        return ud.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
    public ImageView imageView;
        public TextView name;


        public ImageViewHolder(View itemView) {
            super(itemView);
            name= itemView.findViewById(R.id.namw);
            imageView = itemView.findViewById(R.id.img);


        }
    }

}