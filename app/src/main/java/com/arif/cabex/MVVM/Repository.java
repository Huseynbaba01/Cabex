package com.arif.cabex.MVVM;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.arif.cabex.model.RegisterData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Repository {
    static Repository repository;
    private ArrayList<RegisterData> dataList=new ArrayList<>();
    DatabaseReference dataBase;


    public static Repository getRepository(){
        if(repository==null)
            repository=new Repository();
        return repository;
    }

    MutableLiveData<ArrayList<RegisterData>> getLiveData(){
        addDataToTheList();
        MutableLiveData<ArrayList<RegisterData>> liveData=new MutableLiveData<>();
        liveData.setValue(dataList);
        return liveData;
    }

    private void addDataToTheList() {
        dataBase= FirebaseDatabase.getInstance().getReference();
        dataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshotChild:snapshot.getChildren()){
                    dataList.add(new RegisterData(snapshot.getValue(RegisterData.class).getUserName()
                            ,snapshot.getValue(RegisterData.class).getPassword()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
