package com.example.androidsandbox.ui;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidsandbox.JournalListActivity;
import com.example.androidsandbox.R;
import com.example.androidsandbox.model.Journal;

import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class JournalRecyclerAdapter extends RecyclerView.Adapter<JournalRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<Journal> journalList;

    public JournalRecyclerAdapter(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public JournalRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.journal_row, viewGroup,false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull JournalRecyclerAdapter.ViewHolder holder, int position) { // onBindViewHolder ist wie eine For Schleife und lädt die Journals aus der position in den holder...
        Journal journal = journalList.get(position);
        String imageUrl;

        holder.journalTitle.setText(journal.getJournalTitle());
        holder.journalText.setText(journal.getJournalText());
        holder.thoughts.setText(journal.getThoughts());
        holder.name.setText(journal.getUserName());
        imageUrl = journal.getJournalImage();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(journal.getTimeAdded().getSeconds()*1000);
        holder.dateAdded.setText(timeAgo);

        /**


        Glide.with(context).load(imageUrl)
                //.placeholder()
                .fitCenter()
                .into(holder.image);
         */
    }

    @Override
    public int getItemCount() {
        return journalList.size();
    }
// Der ViewHolder ist eine Journal Class....
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView journalTitle, journalText, dateAdded, name, thoughts;
        public String userId, userName;
        public ImageView image;
        public ImageView shareButton;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            // Gehört zu Journal_row.xml
            journalTitle = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thought_list);
            journalText = itemView.findViewById(R.id.journal_text_list);
            dateAdded = itemView.findViewById(R.id.journal_timestamp_list);
            image = itemView.findViewById(R.id.journal_image_list);
            name = itemView.findViewById(R.id.journal_row_username);
            shareButton = itemView.findViewById(R.id.journal_row_share_button);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v("SHARE", "Post wirde Geschärt");
                }
            });

        }
    }
}
