package debasishbarmandevoleper.com.miniproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

import Adapter.horizontalAdapter;
import Adapter.myResAdapter;
import Models.randomModel;
import Models.resModel;

public class allCategory extends AppCompatActivity {

    RecyclerView mainRview;
    myResAdapter adapter;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_category);

        mainRview=findViewById(R.id.mainRec);
        mainRview.setLayoutManager(new GridLayoutManager(this,2));

        db.collection("Users").document(auth.getCurrentUser().getUid()).get()
        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if (documentSnapshot.exists()) {
                   // Toast.makeText(allCategory.this, "inside exist"+documentSnapshot.getString("pincode"), Toast.LENGTH_SHORT).show();
                    String pincode = documentSnapshot.getString("pincode");
                    if (pincode != null) {
                        CollectionReference reference = db.collection("Admin");
                        Query query = reference.whereEqualTo("pincode", pincode);
                        FirestoreRecyclerOptions<resModel> options=new FirestoreRecyclerOptions.Builder<resModel>()
                                .setQuery(query,resModel.class)
                                .build();
                        adapter=new myResAdapter(options);
                        mainRview.setAdapter(adapter);
                        adapter.startListening();
                    } else {
                        startActivity(new Intent(allCategory.this, edit_user.class));


                    }
                }
            }
        });
        //
    }

    @Override
    protected void onStart() {
        super.onStart();
       // adapter.startListening();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}