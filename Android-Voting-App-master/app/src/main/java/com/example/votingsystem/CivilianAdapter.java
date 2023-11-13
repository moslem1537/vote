package com.example.votingsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CivilianAdapter extends RecyclerView.Adapter<CivilianViewHolder> {
    private final Context context;
    private final List<CivilianCandidateList> civilianCandidateList;
    private OnItemClickListener listener;

    public CivilianAdapter(Context context, List<CivilianCandidateList> civilianCandidateList) {
        this.context = context;
        this.civilianCandidateList = civilianCandidateList;
    }

    @NonNull
    @Override
    public CivilianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.activity_civilian_candidate_view_and_vote_items, parent, false);
        return new CivilianViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CivilianViewHolder holder, int position) {
        CivilianCandidateList civilianCandidate = civilianCandidateList.get(position);
        holder.bind(civilianCandidate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(civilianCandidate);
                }
            }
        });

        holder.btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onVoteButtonClick(civilianCandidate);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return civilianCandidateList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(CivilianCandidateList civilianCandidate);
        void onVoteButtonClick(CivilianCandidateList civilianCandidate);
    }
}
