package music.music.com.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

import music.music.com.myapplication.Model.Music;


/**
 * Created by Kalpesh on 23-05-2020.
 */

public class MusicAdapter  extends RecyclerView.Adapter<MusicAdapter.ViewHolder>{

    public Context context;
    public LayoutInflater inflater;
    List<Music> data = Collections.emptyList();
    Music current;
    int currentPos = 0;
    int numtest;
    SQLiteDatabase database;
    boolean add = true;
    public  static String addsum1;

    public MusicAdapter(Context context, List<Music> data){

        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new MusicAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MusicAdapter.ViewHolder holder, int position) {


        try {

            Music current = data.get(position);

            holder.tv1.setText(current.name);
            holder.tv2.setText(current.trackCensoredName);


           Picasso.with(context).load(current.image).into(holder.circleImageView);

        }

        catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {

        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv1,tv2,tv3;

        ImageView circleImageView;


        public ViewHolder(View itemView) {
            super(itemView);

            tv1=(TextView)itemView.findViewById(R.id.dmane);
            tv2=(TextView)itemView.findViewById(R.id.tv_dept);

           circleImageView=(ImageView) itemView.findViewById(R.id.img_profile);





        }
    }
}
