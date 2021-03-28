package Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.zip.Inflater;

import Models.trackModel;
import debasishbarmandevoleper.com.miniproject.R;

public class trackAdapter extends FirestoreRecyclerAdapter<trackModel,trackAdapter.viewHolder> {


    Context context;

    public trackAdapter(@NonNull FirestoreRecyclerOptions<trackModel> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder,final int position, @NonNull trackModel model) {
        holder.title.setText(model.getTitle());
        holder.price.setText(model.getPrice());
        holder.quantity.setText(model.getQuantity());
        holder.date.setText(model.getOrder_date());

        DocumentSnapshot snapshot=getSnapshots().getSnapshot(holder.getAdapterPosition());
        String getStatus=snapshot.getString("status");

        if(Integer.parseInt(getStatus)==2){
            holder.st.setText("Will be delivered shortly");
            holder.status.setBackgroundColor(Color.parseColor("#54e346"));
            // holder.status.setBackgroundColor(Color.parseColor(""));
        }

        if(Integer.parseInt(getStatus)==1){
            holder.st.setText("Your order is under process");
            holder.status.setBackgroundColor(Color.parseColor("#ef8d32"));
        }

    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.track_row,parent,false);
        return new viewHolder(v);
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView price,address,quantity,title,st,date;
        Button status;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textView63);
            quantity=itemView.findViewById(R.id.textView65);
            price=itemView.findViewById(R.id.textView64);
            status=itemView.findViewById(R.id.button18);
            st=itemView.findViewById(R.id.textView66);
            date=itemView.findViewById(R.id.textView69);
        }
    }
}
