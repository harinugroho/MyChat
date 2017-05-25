package es.esy.revolusipingwin.mychat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter yang di gunakan untuk membentik tampilan pada list chat nantinya
 * dan di dalamnya juga ada filter yang berguna untuk mengecek pesan itu di kirim oleh siapa,
 * jika di kirim oleh kita maka akan di letakkan di kanan jika bukan akan di letakkkan di kiri
 * Created by Hari Nugroho on 24/05/2017.
 */

public class ChatAdapter extends ArrayAdapter<Chat> {
    private static final String USER_ID = FirebaseAuth.getInstance().getCurrentUser().getUid();

    /**
     * contructor dari chat adapter
     * @param context class context
     * @param objects array list yang akan di tampilkan
     */
    public ChatAdapter(@NonNull Context context, @NonNull ArrayList<Chat> objects) {
        super(context, 0, objects);
    }

    /**
     * untuk seting tampilan dan pembuatan item yang akan di tampilkan
     * @param position menunjukkan di baris ke berapa
     * @param convertView layout inflater
     * @param parent
     * @return hasil inflater atau hasil dari pembuatan list
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_chat,parent,false);
        }
        Chat currnetData = getItem(position); // get data from Consument class

        TextView viewName = (TextView) convertView.findViewById(R.id.view_name);
        viewName.setText(currnetData.getUserName());

        TextView viewMassage = (TextView) convertView.findViewById(R.id.view_massage);
        viewMassage.setText(currnetData.getMassage());

        TextView viewDate = (TextView) convertView.findViewById(R.id.view_date);
        Date date = new Date(currnetData.getTime()*1000L); // use for change long to dateString
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy 'at' hh:mm aaa"); // use for date format
        viewDate.setText(sdf.format(date));

        ImageView pictureRight = (ImageView) convertView.findViewById(R.id.picture_right);
        ImageView pictureLeft = (ImageView) convertView.findViewById(R.id.picture_left);
        LinearLayout viewGroupMassage = (LinearLayout) convertView.findViewById(R.id.view_group_massage);
        if(currnetData.getUserId().equals(USER_ID)){
            pictureRight.setVisibility(View.VISIBLE);
            pictureLeft.setVisibility(View.GONE);
            viewDate.setGravity(Gravity.START);
            viewMassage.setGravity(Gravity.END);
            viewName.setGravity(Gravity.END);
            viewGroupMassage.setPadding(90, 0, 6, 0);
        } else {
            pictureRight.setVisibility(View.GONE);
            pictureLeft.setVisibility(View.VISIBLE);
            viewDate.setGravity(Gravity.END);
            viewMassage.setGravity(Gravity.START);
            viewName.setGravity(Gravity.START);
            viewGroupMassage.setPadding(6, 0, 90, 0);
        }

        return convertView;
    }
}
