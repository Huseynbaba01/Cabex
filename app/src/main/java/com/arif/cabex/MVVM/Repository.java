package com.arif.cabex.MVVM;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.arif.cabex.model.LoginData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Repository {
    static Repository repository;
    private ArrayList<LoginData> dataList=new ArrayList<>();
    DatabaseReference dataBase;


    public static Repository getRepository(){
        if(repository==null)
            repository=new Repository();
        return repository;
    }

    MutableLiveData<ArrayList<LoginData>> getLiveData(){
        addDataToTheList();
        MutableLiveData<ArrayList<LoginData>> liveData=new MutableLiveData<>();
        liveData.setValue(dataList);
        return liveData;
    }

    private void addDataToTheList() {
        dataBase= FirebaseDatabase.getInstance().getReference();
        dataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshotChild:snapshot.getChildren()){
                    dataList.add(new LoginData(snapshot.getValue(LoginData.class).getUserName()
                            ,snapshot.getValue(LoginData.class).getPassword()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
