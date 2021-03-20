package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import Models.resModel;
import debasishbarmandevoleper.com.miniproject.R;
import debasishbarmandevoleper.com.miniproject.User_Profile;

public class myResAdapter extends FirestoreRecyclerAdapter<resModel,myResAdapter.viewHolder> {

    Context context;
    public myResAdapter(@NonNull FirestoreRecyclerOptions<resModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull resModel model) {

        holder.catContainer.setAnimation(AnimationUtils.loadAnimation(holder.textView.getContext(),R.anim.fade));
        holder.textView.setText(model.getName());
        Glide.with(holder.textView.getContext()).load(model.getImageUri()).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(holder.textView.getContext(), ""+holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
               /* holder.db.collection("AdminFood").document(holder.auth.getCurrentUser().getUid()).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String res=documentSnapshot.getString("user")
                            }
                        })*/
                DocumentSnapshot snapshot=getSnapshots().getSnapshot(holder.getAdapterPosition());
                String id=snapshot.getId();
                SharedPreferences sharedPreferences=holder.textView.getContext().getSharedPreferences("name",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("id",id);
                editor.apply();
                Intent i=new Intent((Activity)holder.textView.getContext(), User_Profile.class);
                ((Activity)holder.textView.getContext()).startActivity(i);
                ((Activity) holder.textView.getContext()).finish();
               // Toast.makeText(holder.textView.getContext(), ""+id, Toast.LENGTH_SHORT).show();


            }
        });
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        return new viewHolder(v);
    }

    class viewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        ImageView imageView;
        ConstraintLayout catContainer;
        private FirebaseAuth auth=FirebaseAuth.getInstance();
        private FirebaseFirestore db=FirebaseFirestore.getInstance();
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            catContainer=itemView.findViewById(R.id.catContainer);
            textView=itemView.findViewById(R.id.pizza);
            imageView=itemView.findViewById(R.id.imageView8);

        }
    }
}
