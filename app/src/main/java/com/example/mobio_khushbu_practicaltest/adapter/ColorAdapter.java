package com.example.mobio_khushbu_practicaltest.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mobio_khushbu_practicaltest.R;
import com.example.mobio_khushbu_practicaltest.model.Color;
import com.example.mobio_khushbu_practicaltest.model.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ColorAdapter extends  RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private ArrayList<Color> list;
    Context context;
    private int lastSelectedPosition = -1;

    public ColorAdapter(Context ctx, ArrayList<Color> list) {
        this.list = list;
        context = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.rdColor.setText(list.get(position).getColorName());
        holder.rdColor.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rdColor) RadioButton rdColor;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            rdColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    for (int i=0;i<list.size();i++){
                        if (i==getAdapterPosition()){
                            list.get(getAdapterPosition()).setSelected(true);
                        }else {
                            list.get(getAdapterPosition()).setSelected(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }

    public String getSelectedColor(){
        String selectedItems="";
        try {
            if (list != null)
            {
                for (int index=0;index<list.size();index++)
                {
                    if (list.get(index).isSelected())
                    {
                        selectedItems = ""+list.get(index).getColorName();
                    }
                }
            }
        }
        catch (Exception e){e.printStackTrace();}
        return selectedItems;
    }
    /*public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        private ImageView views;

        public DownloadImage(ImageView views){
            this.views = views;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {
            String imageURL = URL[0];
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(imageURL).getContent());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            views.setImageBitmap(result);
            Log.e("result","==> "+ result);
        }
    }*/
}
