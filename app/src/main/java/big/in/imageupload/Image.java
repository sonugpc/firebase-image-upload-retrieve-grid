package big.in.imageupload;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Image extends AppCompatActivity {
    private RecyclerView mRCV;
    private Madapter mADV;
    private DatabaseReference datarf;
    private List<ImgData> mlist;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mRCV= findViewById(R.id.recycler_view);
        mRCV.setHasFixedSize(true);
        mRCV.setLayoutManager(new GridLayoutManager(this,3));
        mlist= new ArrayList<>();
        datarf = FirebaseDatabase.getInstance().getReference();

        datarf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot Psnap:dataSnapshot.getChildren())
                {
                    ImgData i= Psnap.getValue(ImgData.class);
                    mlist.add(i);
                }
                mADV = new Madapter(Image.this,mlist);

                mRCV.setAdapter(mADV);




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
