package com.vikas.secret.ui.angrymode;

import com.vikas.secret.data.models.AngerModel;

import java.util.List;

public interface AngerCallbacks {
    void onGetAngerList(List<AngerModel> messages);
}
