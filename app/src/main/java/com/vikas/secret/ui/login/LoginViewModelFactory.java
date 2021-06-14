package com.vikas.secret.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.vikas.secret.data.GmailLoginRepository;

import org.jetbrains.annotations.NotNull;

public class LoginViewModelFactory implements ViewModelProvider.Factory{

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(GmailLoginViewModel.class)) {
            return (T) new GmailLoginViewModel(GmailLoginRepository.getInstance());
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
