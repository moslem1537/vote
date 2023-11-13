package com.example.votingsystem;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CivilianViewHolder extends RecyclerView.ViewHolder {
    TextView txtName;
    TextView txtCity;
    TextView txtAchievements;
    TextView txtManifesto;
    Button btnVote; // Add this line

    public CivilianViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = itemView.findViewById(R.id.apName);
        txtCity = itemView.findViewById(R.id.apCity);
        txtAchievements = itemView.findViewById(R.id.apAchievements);
        txtManifesto = itemView.findViewById(R.id.apManifesto);
        btnVote = itemView.findViewById(R.id.btnVote); // Add this line
    }

    public void bind(CivilianCandidateList civilianCandidate) {
        txtName.setText(civilianCandidate.getName());
        txtCity.setText(civilianCandidate.getCity());
        txtAchievements.setText(civilianCandidate.getAchievements());
        txtManifesto.setText(civilianCandidate.getManifesto());
    }
}


