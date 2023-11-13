package com.example.votingsystem;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import java.util.List;

public class Civilian_Candidate_View_and_VoteActivity extends AppCompatActivity {

    private static final String TAG = "VoteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_civilian_candidate_view_and_vote);
// Find the back button and set an OnClickListener to it
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the back button click event
                onBackPressed();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.civiliancandidatesRecyclerView);
        DatabaseHelper db = new DatabaseHelper(this);

        // Retrieve the logged-in civilian's email from the session
        SharedPreferences prefs = getSharedPreferences("MyApp", MODE_PRIVATE);
        String civilianEmail = prefs.getString("civilianEmail", "");

        // Retrieve the logged-in civilian's city
        String civiliancity = db.getCivilianCity(civilianEmail);

        List<CivilianCandidateList> civilianCandidateList = db.getApprovedCandidatesByCity(civiliancity);

        CivilianAdapter adapter = new CivilianAdapter(this, civilianCandidateList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemClickListener(new CivilianAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CivilianCandidateList civilianCandidate) {
                // This method is not used for voting, so we can leave it empty
            }

            @Override
            public void onVoteButtonClick(CivilianCandidateList civilianCandidate) {
                // Log the button press
                Log.d(TAG, "Button clicked for candidate: " + civilianCandidate.getName());

                String selectedCandidate = civilianCandidate.getName();

                SharedPreferences sharedPreferences = getSharedPreferences("VotingSession", MODE_PRIVATE);
                boolean isVotingSessionActive = sharedPreferences.getBoolean("isVotingSessionActive", true);

                if (!isVotingSessionActive) {
                    Toast.makeText(Civilian_Candidate_View_and_VoteActivity.this, "Voting has stopped.", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean alreadyVoted = db.hasVoted(civilianEmail);
                if (alreadyVoted) {
                    Toast.makeText(Civilian_Candidate_View_and_VoteActivity.this, "You have already voted for a candidate. Please wait for the voting results.", Toast.LENGTH_SHORT).show();
                } else {
                    db.incrementVoteCount(selectedCandidate);
                    db.insertVoteRecord(civilianEmail, selectedCandidate, civiliancity);
                    Toast.makeText(Civilian_Candidate_View_and_VoteActivity.this, "Vote recorded successfully.", Toast.LENGTH_SHORT).show();

                    // Remove the selected candidate from the list
                    civilianCandidateList.remove(civilianCandidate);
                    adapter.notifyDataSetChanged();

                    // Check if there are remaining candidates
                    if (civilianCandidateList.isEmpty()) {
                        // No more candidates, finish the activity
                        finish();
                    } else {
                        // Scroll to the next candidate
                        recyclerView.scrollToPosition(0);
                    }
                }
            }
        });
    }
}



