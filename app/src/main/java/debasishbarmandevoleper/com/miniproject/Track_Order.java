package debasishbarmandevoleper.com.miniproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import Adapter.trackAdapter;
import Models.trackModel;

public class Track_Order extends AppCompatActivity {

    RecyclerView list;
    trackAdapter adapter;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track__order);
        auth=FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        list=findViewById(R.id.orderRecview);
        list.setLayoutManager(new LinearLayoutManager(this));

        CollectionReference reference=db.collection("DeliveryList");
        Query query=reference.whereEqualTo("user_id",auth.getCurrentUser().getUid());
        FirestoreRecyclerOptions<trackModel> options=new FirestoreRecyclerOptions.Builder<trackModel>()
                .setQuery(query, trackModel.class)
                .build();

         adapter=new trackAdapter(options);
         list.setAdapter(adapter);
       // adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}