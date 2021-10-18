package com.example.medicare;

import android.content.Context;
import androidx.annotation.NonNull;

import com.example.medicare.data.source.MedicineRepository;
import com.example.medicare.data.source.local.MedicinesLocalDataSource;

public class Injection {

    public static MedicineRepository provideMedicineRepository(@NonNull Context context) {
        return MedicineRepository.getInstance(MedicinesLocalDataSource.getInstance(context));
    }
}
