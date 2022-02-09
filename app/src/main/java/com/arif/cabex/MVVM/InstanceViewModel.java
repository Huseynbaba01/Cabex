package com.arif.cabex.MVVM;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.arif.cabex.DataHolder.LoginData;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class InstanceViewModel extends ViewModel {
    public MutableLiveData<ArrayList<LoginData>> livedata;
    public Repository repo;

    public void declareLiveData(){
        repo=Repository.getRepository();
        livedata=repo.getLiveData();
    }

    public LiveData<ArrayList<LoginData>> getViewHolderList(){
        return livedata;
    }

    public Boolean checkLogin(String userName,String password){
        for(LoginData data: livedata.getValue()){
            if(Objects.equals(data.getUserName(), userName) && Objects.equals(data.getPassword(), password)){
                return true;
            }
        }
        return false;
    }

    public void addAccountInformations(String userName,String password){
        DatabaseReference database= FirebaseDatabase.getInstance().getReference();
        String id= database.push().getKey();
        database.child(id).setValue(new LoginData(userName,password));
    }

}
