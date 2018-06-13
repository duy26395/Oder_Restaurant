package com.example.duy26.app1.Admin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.duy26.app1.Data;
import com.example.duy26.app1.R;

import java.util.ArrayList;

public class Adapter_Customer_MN extends RecyclerView.Adapter<Adapter_Customer_MN.Viewholder> implements Filterable{
    private ArrayList<Data> data;
    private ArrayList<Data> filteredUserList;
    private Context context;
    private Onitemclick onitemclick;

    public Adapter_Customer_MN(ArrayList<Data> dataArrayList, Context context, Onitemclick onitemclick) {
        this.data = dataArrayList;
        this.filteredUserList = dataArrayList;
        this.context = context;
        this.onitemclick = onitemclick;
    }
    @NonNull
    @Override
    public Adapter_Customer_MN.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_mn_customer,parent,false);
        final Adapter_Customer_MN.Viewholder viewholder = new Adapter_Customer_MN.Viewholder(itemview);
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onitemclick.click(filteredUserList.get(viewholder.getAdapterPosition()),viewholder.getAdapterPosition());
            }
        });
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {
        holder.bindata(filteredUserList.get(position));
//        final String name = filteredUserList.get(position).getName();
//        final String phone = filteredUserList.get(position).getPhonenumber();
//        final String address = filteredUserList.get(position).getAddress();
//        final String email = filteredUserList.get(position).getEmail();
//        holder.setClick(new Click() {
//            @Override
//            public void onItem(View v, int post) {
////                openfragment(name,phone,address,email);
////                Fragment_customer_insert customer_insert = new Fragment_customer_insert();
////                customer_insert.show(((AppCompatActivity)context).getFragmentManager(),null);
//
//            }
//
//            private void openfragment(String name, String phone, String address, String email) {
//                Bundle bundle = new Bundle();
//                bundle.putString("NAME", name);
//                bundle.putString("PHONE", phone);
//                bundle.putString("ADDRESS", address);
//                bundle.putString("EMAIL", email);
//
//                Fragment_customer_insert Fragment = new Fragment_customer_insert();
//                Fragment.setArguments(bundle);
//                Fragment.show(((AppCompatActivity)context).getFragmentManager(),null);
//                Log.e("______!________",name);
//
//            }
//        });

        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(data.get(position).isSelected());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                filteredUserList.get(holder.getAdapterPosition()).setSelected(isChecked);
            }
        });
    }


    @Override
    public int getItemCount() {

        return filteredUserList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()) {
                    filteredUserList = data;
                } else {
                    ArrayList<Data> dataArrayList = new ArrayList<>();
                    for (Data user : data) {
                        if (user.getPhonenumber().toLowerCase().contains(constraint)) {

                            dataArrayList.add(user);
                        }
                    }
                    filteredUserList = dataArrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredUserList = (ArrayList<Data>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        private TextView textname,textphone;
        private CheckBox checkBox;
        private Click click;

        public Viewholder(View itemView) {
            super(itemView);
            textname = (TextView)itemView.findViewById(R.id.manager_customer_ten);
            textphone = (TextView)itemView.findViewById(R.id.manager_customer_sdt);
            checkBox = (CheckBox)itemView.findViewById(R.id.manager_customer_checkbox);
        }
        public void bindata(Data data){
            textname.setText(data.getName());
            textphone.setText(data.getPhonenumber());

        }
    }
    public void update(ArrayList<Data> todolist){
        this.data = todolist;
        notifyDataSetChanged();
    }
}
