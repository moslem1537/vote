package com.example.votingsystem;

import android.view.View;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ApprovedCandidateViewHolder extends RecyclerView.ViewHolder {
     TextView txtName;
     TextView txtCity;
     TextView txtAchievements;
     TextView txtManifesto;


    public ApprovedCandidateViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.apName);
        txtCity = itemView.findViewById(R.id.apCity);
        txtAchievements = itemView.findViewById(R.id.apAchievements);
        txtManifesto = itemView.findViewById(R.id.apManifesto);
    }

    public void bind(ApprovedCandidate candidate) {
        txtName.setText(candidate.getName());
        txtCity.setText(candidate.getCity());
        txtAchievements.setText(candidate.getAchievements());
        txtManifesto.setText(candidate.getManifesto());
    }
}

