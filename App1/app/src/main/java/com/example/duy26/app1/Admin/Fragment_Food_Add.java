package com.example.duy26.app1.Admin;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duy26.app1.Connectionclass;
import com.example.duy26.app1.R;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class Fragment_Food_Add extends android.support.v4.app.DialogFragment {
    private static final int RESULT_LOAD_IMAGE = 1;
    public static final int REQUEST_CODE_THIS = 3;
    private EditText name,details,price,url_img,id;
    Connectionclass connectionclass;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> data_food_types;
    private MaterialBetterSpinner spinner;
    private String name_gr,encodedImage,id_gr;
    private ImageView imagebox;
    private byte[] byteArray;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_add_mn, container, false);

        id = (EditText) view.findViewById(R.id.food_add_id);
        name = (EditText) view.findViewById(R.id.food_add_name);
        price = (EditText) view.findViewById(R.id.food_add_price);
        details = (EditText) view.findViewById(R.id.food_add_details);
        url_img = (EditText) view.findViewById(R.id.food_add_img);
        imagebox = (ImageView) view.findViewById(R.id.food_add_imageView);

        (view.findViewById(R.id.food_add_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        data_food_types = new ArrayList<String>();
        gdata_server_type_food();
        spinner = (MaterialBetterSpinner) view.findViewById(R.id.food_add_type);
        arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, data_food_types);
        spinner.setAdapter(arrayAdapter);
//        spinner.setHint("Chọn Loại Sản Phẩm");
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                name_gr = (String) parent.getItemAtPosition(position).toString().trim();
            }
        });



        url_img.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String last = url_img.getText().toString().trim();
                if (last.contains("http"))
                {
                    Glide.with(getActivity()).load(last).into(imagebox);
                }
            }
        });

        Button search_img = view.findViewById(R.id.search_add_img);
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

        Button button = (Button)view.findViewById(R.id.food_add_confirm);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_gr =  get_defaul_name(name_gr);
                String id1 = id.getText().toString().trim();
                String ten1 = name.getText().toString().trim();
                String price1 = price.getText().toString().trim();
                String details1 = details.getText().toString().trim();
                String hinh = url_img.getText().toString().trim();

                Intent intent = new Intent();
                intent.putExtra("ID_AF",id1);
                intent.putExtra("NAME_AF", ten1);
                intent.putExtra("PRICE_AF", price1);
                intent.putExtra("DETAILS_AF",details1);
                intent.putExtra("IDGR_AF", id_gr);
                if (url_img.getText().toString().contains("http"))
                { intent.putExtra("IMG_AF",hinh); }
                else {     intent.putExtra("IMG_AF",encodedImage);   }
                getTargetFragment().onActivityResult(getTargetRequestCode(),REQUEST_CODE_THIS,intent);

                id.setText("");
                name.setText("");
                price.setText("");
                details.setText("");
                spinner.setHint("Chọn Loại Sản Phẩm");
                url_img.setText("");
                imagebox.setImageURI(null);
            }
        });
        return  view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            // getting the selected image, setting in imageview and converting it to byte and base 64
//            progressBar.setVisibility(View.VISIBLE);
            Bitmap originBitmap = null;
            Uri selectedImage = data.getData();
            Toast.makeText(getActivity(), selectedImage.toString(), Toast.LENGTH_LONG).show();
            InputStream imageStream;
            try {
//                imageStream = getContentResolver().openInputStream(selectedImage);
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
                    image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();
                    encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    // Calling the background process so that application wont slow down
//                    UploadImage uploadImage = new UploadImage();
//                    uploadImage.execute("");
                    //End Calling the background process so that application wont slow down
                } catch (Exception e) { }
                Toast.makeText(getActivity(), "Conversion Done", Toast.LENGTH_SHORT).show();
                url_img.setText(encodedImage);
                Log.e("+_+",encodedImage);
            }
        } else {
            System.out.println("Error Occured");
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
    private String get_defaul_name(String hihi) {
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
}
