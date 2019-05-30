package com.example.user.wardrobe2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WardrobeFragment extends Fragment {

    FloatingActionButton addBtn;

    private RecyclerView wardrobeRecyclerView;
    private List<Clothes> listClothes = new ArrayList<>();
    int namePosition = 1;
    WardrobeRecyclerViewAdapter recyclerAdapter;

    private FirebaseDatabase database;
    private DatabaseReference positionRef;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private ChildEventListener childEventListener;

    public WardrobeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_wardrobe, container, false);

        FirebaseInit();

        wardrobeRecyclerView = v.findViewById(R.id.wardrobe_recyclerView);
        //???????????????????????????
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(, 2);
        wardrobeRecyclerView.setLayoutManager(mGridLayoutManager);
        recyclerAdapter = new WardrobeRecyclerViewAdapter(getContext(), listClothes);
        wardrobeRecyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickListener(new WardrobeRecyclerViewAdapter.OnItemClickListener() {

            @Override
            public void onStatusClick(int position) {
                if (listClothes.get(position).isVisible()) {

                } else {

                }
            }
        });


        addBtn = v.findViewById(R.id.add_clothing);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //переход на AddingClothesActivity
                //Intent intent = new Intent(getApplicationContext(), AddingClothesActivity.class);
                //startActivity(intent);

            }
        });


        listClothes.clear();


        return v;
    }

    private void FirebaseInit() {

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child(mAuth.getCurrentUser().getUid()).child("notes");
        positionRef = database.getReference().child(mAuth.getCurrentUser().getUid()).child("namePosition");

        childEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                listClothes.add(dataSnapshot.getValue(Clothes.class));
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Clothes clothes = dataSnapshot.getValue(Clothes.class);
                int index = getItemIndex(clothes);
                listClothes.set(index, clothes);
                recyclerAdapter.notifyItemChanged(index);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Clothes clothes = dataSnapshot.getValue(Clothes.class);
                int index = getItemIndex(clothes);
                listClothes.remove(index);
                recyclerAdapter.notifyItemChanged(index);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        myRef.addChildEventListener(childEventListener);

        readPositionFromDatabase();
    }

    @Override
    public void onStop() {
        super.onStop();
        myRef.removeEventListener(childEventListener);
        childEventListener = null;
    }

    private int getItemIndex(Clothes clothes) {

        int index = -1;
        for (int i = 0; i < listClothes.size(); i++) {
            if (listClothes.get(i).getKey().equals(clothes.getKey())) {
                index = i;
                break;
            }
        }
        return index;
    }

    private void removeClothes(int position) {
        myRef.child(listClothes.get(position).getKey()).removeValue();
    }

    private void addClothes() {
        String id = myRef.child(mAuth.getCurrentUser().getUid()).push().getKey();
        Clothes newNote = new Clothes();
        //выгрузить фото

        Map<String, Object> noteValue = newNote.toMap();

        Map<String, Object> clothes = new HashMap<>();
        clothes.put(id, noteValue);

        myRef.updateChildren(clothes);
        namePosition++;
        writePositionToDatabase();

    }


    private void writePositionToDatabase() {
        positionRef.setValue(namePosition);
    }

    private void readPositionFromDatabase() {
        // Чтение с database
        positionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int value = dataSnapshot.getValue(Integer.class);
                namePosition = value;
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

}
