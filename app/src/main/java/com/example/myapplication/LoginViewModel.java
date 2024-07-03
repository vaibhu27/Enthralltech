package com.example.myapplication;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Data.model.model.User;
import com.example.myapplication.Data.model.repository.UserRepository;

public class LoginViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<Boolean> loginResult = new MutableLiveData<>();

    public LoginViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public MutableLiveData<Boolean> getLoginResult() {
        return loginResult;
    }

    public void login(String mobile, String mpin) {
        User user = userRepository.getUser(mobile);
        if (user != null && user.getMpin().equals(mpin)) {
            loginResult.setValue(true);
        } else {
            loginResult.setValue(false);
        }
    }

    public void addUser(String mobile, String mpin) {
        User user = new User(mobile, mpin);
        userRepository.addUser(user);
    }
}
