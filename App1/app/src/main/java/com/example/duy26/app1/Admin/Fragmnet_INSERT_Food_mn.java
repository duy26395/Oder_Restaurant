package com.example.duy26.app1.Admin;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.camera2.TotalCaptureResult;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AndroidRuntimeException;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class Fragmnet_INSERT_Food_mn extends DialogFragment {
    private Connectionclass connectionclass;
    private String id,id_gr,img;
    private String type_id;
    private String mTvCountry;
    private String name_group;
    private AdapterView adapterView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> data_food_types;
    private EditText ten, gia, mota, hinhanh;
    private MaterialBetterSpinner spinner;

    byte[] byteArray;
    final int RESULT_LOAD_IMAGE = 1;
    private ImageView imagebox;
    String encodedImage;
    public static final int REQUEST_UPDATE = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert_food_mn, container, false);

        imagebox = (ImageView) view.findViewById(R.id.imageView);

        final String name_f = getArguments().getString("NAME");
        type_id = getArguments().getString("TYPE");
        String address_f = getArguments().getString("ADDRESS");
        String email_f = getArguments().getString("EMAIL");
        img = getArguments().getString("IMAGE");
        id = getArguments().getString("ID");
        name_group = getArguments().getString("NAME_TYPE");
        Toast.makeText(getActivity(),name_group,Toast.LENGTH_SHORT).show();

        (view.findViewById(R.id.food_ins_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        try {
            if (img.contains("http")) {
                Glide.with(getActivity())
                        .load(img)
                        .into(imagebox);
            } else Glide.with(getActivity())
                    .load(decodeBase64(img))
                    .into(imagebox);
        }catch (Exception e){}

        ten = (EditText) view.findViewById(R.id.food_ins_name);
        gia = (EditText) view.findViewById(R.id.food_ins_price);
        mota = (EditText) view.findViewById(R.id.food_ins_details);
        hinhanh = (EditText) view.findViewById(R.id.food_ins_img);

        ten.setText(name_f);
        gia.setText(address_f);
        mota.setText(email_f);
        hinhanh.setText(img);

        data_food_types = new ArrayList<String>();
        gdata_server_type_food();


        spinner = (MaterialBetterSpinner) view.findViewById(R.id.food_ins_type);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, data_food_types);
        spinner.setAdapter(arrayAdapter);
        spinner.setHint(name_group);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mTvCountry = (String) parent.getItemAtPosition(position).toString().trim();
            }
        });
        if(mTvCountry == null)
        {
            mTvCountry = name_group;
        }

        hinhanh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String last = hinhanh.getText().toString().trim();
                if (last.contains("http"))
                {
                    Glide.with(getActivity()).load(last).into(imagebox);
                }
            }
        });

        Button cofirm = view.findViewById(R.id.food_ins_confirm);
        cofirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                id_gr = new String();
                id_gr = get_defaul_name(mTvCountry);

                String name_food = ten.getText().toString().trim();
                String gia_food = gia.getText().toString();
                String mota_food = mota.getText().toString();
                String hinh = hinhanh.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("ID_KEY",id);
                intent.putExtra("ID_GROUP",id_gr);
                if (hinhanh.getText().toString().contains("http"))
                { intent.putExtra("IMG_F",hinh); }
                else {     intent.putExtra("IMG_F",encodedImage);   }

                intent.putExtra("NAME_FOOD",name_food);
                intent.putExtra("PRICE_FOOD",gia_food);
                intent.putExtra("MOTA_FOOD",mota_food);


                getTargetFragment().onActivityResult(getTargetRequestCode(),REQUEST_UPDATE,intent);
            }
        });
        Button search_img = view.findViewById(R.id.search_img);
        search_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), RESULT_LOAD_IMAGE);
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE );
            }
        });
        setRetainInstance(true);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialogFragment = getDialog();
        if (dialogFragment != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialogFragment.getWindow()).setLayout(width, height);
        }
    }

    private void gdata_server_type_food() {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();
        try {
            String query5 = "SELECT id_Group,Name_group FROM [Group_Food]";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query5);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        data_food_types.add(resultSet.getString("Name_group"));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception ignored) {
        }
        Log.e("LOG_DB_FOOOD_TYPE", String.valueOf(data_food_types));
    }
    private String get_defaul_name(String hihi)
    {
        connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();
        try {
            String query5 = "SELECT id_Group FROM [Group_Food] WHERE Name_group LIKE  N'"+ hihi.trim() +"'";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query5);
            if (resultSet != null) {
                while (resultSet.next()) {
                    try {
                        id_gr = resultSet.getString("id_Group");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }catch (Exception ignored) {        }
        return id_gr;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && requestCode == Activity.RESULT_OK && null != data) {
            // getting the selected image, setting in imageview and converting it to byte and base 64
//            progressBar.setVisibility(View.VISIBLE);
            Bitmap originBitmap = null;
            Uri selectedImage = data.getData();
            Toast.makeText(getActivity(), selectedImage.toString(), Toast.LENGTH_LONG).show();
            InputStream imageStream;
            try {
                imageStream = ((AppCompatActivity)getContext()).getContentResolver().openInputStream(selectedImage);
                originBitmap = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage().toString());
            }
            if (originBitmap != null) {
                this.imagebox.setImageBitmap(originBitmap);
                Log.w("Image Setted in", "Done Loading Image");
                try {
                    Bitmap image = ((BitmapDrawable) imagebox.getDrawable()).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    // Calling the background process so that application wont slow down
//                    UploadImage uploadImage = new UploadImage();
//                    uploadImage.execute("");
                    //End Calling the background process so that application wont slow down
                } catch (Exception e) { }
                Toast.makeText(getActivity(), "Conversion Done", Toast.LENGTH_SHORT).show();
                hinhanh.setText(encodedImage);
                Log.e("+_+",encodedImage);
            }
        } else {
            System.out.println("Error Occured");
        }
    }

    public class UploadImage extends AsyncTask<String, String, String> {
        @Override
        protected void onPostExecute(String r) {
            // After successful insertion of image
//            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Image Inserted Succesfully", Toast.LENGTH_LONG).show();
        }

        @Override
        protected String doInBackground(String... params) {
            // Inserting in the database
            String msg = "unknown";
            try {
                connectionclass = new Connectionclass();
                Connection connection = connectionclass.CONN();
                String insertTableSQL = "INSERT INTO [IMG] "
                        + "(Img) VALUES"
                        + "(?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
                    preparedStatement.setString(1, encodedImage);
                    preparedStatement.executeUpdate();
                    msg = "Inserted Successfully";
                } catch (SQLException ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 1:", msg);
                } catch (IOError ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 2:", msg);
                } catch (AndroidRuntimeException ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 3:", msg);
                } catch (NullPointerException ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 4:", msg);
                } catch (Exception ex) {
                    msg = ex.getMessage().toString();
                    Log.d("Error no 5:", msg);
                }
                System.out.println(msg);
                return "";
                //End Inserting in the database
            } catch (Exception e) {
            }
            return msg;
        }
    }

    public static Bitmap decodeBase64(String input)
    {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds =false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        byte[] decodedBytes = Base64.decode(input,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes,0,decodedBytes.length,options);
    }
}