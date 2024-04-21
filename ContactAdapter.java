package edu.cse470.mycontactlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class ContactAdapter extends RecyclerView.Adapter{
    private ArrayList<Contact> contactData;
    private View.OnClickListener myOnItemClickListener;
    private Context parentContext;
    private boolean isDeleting;

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        public TextView textContactName;
        public TextView textPhone;
        public Button deleteButton;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textContactName = itemView.findViewById(R.id.textContactName);
            textPhone = itemView.findViewById(R.id.textPhoneNumber);
            deleteButton = itemView.findViewById(R.id.buttonDeleteContact);
            itemView.setTag(this);
            itemView.setOnClickListener(myOnItemClickListener);
        }
        public TextView getTextContactName() {
            return textContactName;
        }
        public TextView getPhoneTextView() {
            return textPhone;
        }
        public Button getDeleteButton() {
            return deleteButton;
        }
    }

    public ContactAdapter(ArrayList<Contact> arrayList, Context context) {
        contactData = arrayList;
        parentContext = context;
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        this.myOnItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ContactViewHolder cvh = (ContactViewHolder) holder;
        cvh.getTextContactName().setText(contactData.get(position).getContactName());
        cvh.getPhoneTextView().setText(contactData.get(position).getPhoneNumber());
        if (isDeleting) {
            cvh.getDeleteButton().setVisibility(View.VISIBLE);
            cvh.getDeleteButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteItem(position);
                }
            });
        }
        else {
            cvh.getDeleteButton().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return contactData.size();
    }

    private void deleteItem(int position) {
        Contact contact = contactData.get(position);
        ContactDataSource ds = new ContactDataSource(parentContext);
        try {
            ds.open();
            boolean didDelete = ds.deleteContact(contact.getContactID());
            ds.close();
            if (didDelete) {
                contactData.remove(position);
                notifyDataSetChanged();
            }
            else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e) {
        }
    }

    public void setDelete(boolean b) {
        isDeleting = b;
    }
}