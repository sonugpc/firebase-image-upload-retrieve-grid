package big.in.imageupload;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import android.content.Intent;
import android.content.IntentSender;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private StorageReference StorageRef;
    public DatabaseReference DataRef;
    Button su,go;
    ImageView i;
 public final int P =1;
    String name;
    private Uri path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        su=(Button)findViewById(R.id.su);
       // go=(Button)findViewById(R.id.go);
        su.setOnClickListener(this);
        //go.setOnClickListener(this);
        i=(ImageView)findViewById(R.id.iq);
        StorageRef = FirebaseStorage.getInstance().getReference();
        DataRef = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.view)
        {
            Intent i = new Intent(this, Image.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onClick(View view) {
        if(view==su)
        {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(i.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i, "Select Image"), P);
        }
        /*else
        {
            Intent i = new Intent(this,Image.class);
            startActivity(i);

        }*/

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==P && resultCode==RESULT_OK)
        {
            path=data.getData();
            File f=new File(path.toString());
            try {
                Bitmap n= MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                i.setImageBitmap(n);
                name = f.getName();
                uploadfb();
                Toast.makeText(this,"Uploading to Firebase is Started",Toast.LENGTH_SHORT).show();


            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public  void uploadfb()
    {

        StorageReference riversRef = StorageRef.child(name);

        riversRef.putFile(path)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(MainActivity.this,"File Uploaded",Toast.LENGTH_SHORT).show();
                        ImgData obj=new ImgData(name,taskSnapshot.getDownloadUrl().toString());
                        String id=DataRef.push().getKey();
                        DataRef.child(id).setValue(obj);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                    }
                });
    }
}
