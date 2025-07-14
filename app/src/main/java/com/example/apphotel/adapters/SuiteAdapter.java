package com.example.apphotel.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.apphotel.R;
import com.example.apphotel.ReservationActivity;
import com.example.apphotel.Suite;
import java.util.List;

public class SuiteAdapter extends RecyclerView.Adapter<SuiteAdapter.SuiteViewHolder> {

    private final List<Suite> suiteList;

    public SuiteAdapter(List<Suite> suiteList) {
        this.suiteList = suiteList;
    }

    @NonNull
    @Override
    public SuiteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_suite_card, parent, false);
        return new SuiteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuiteViewHolder holder, int position) {
        Suite suite = suiteList.get(position);
        holder.suiteImage.setImageResource(suite.getImageResId());
        holder.suiteName.setText(suite.getName());
        holder.suiteDescription.setText(suite.getDescription());
        holder.suitePrice.setText(suite.getPrice());

        holder.btnReservar.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, ReservationActivity.class);

            intent.putExtra("SUITE_NAME", suite.getName());
            intent.putExtra("SUITE_PRICE_RAW", 850.0);
            intent.putExtra("SUITE_IMAGE", suite.getImageResId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return suiteList.size();
    }

    static class SuiteViewHolder extends RecyclerView.ViewHolder {
        ImageView suiteImage;
        TextView suiteName, suiteDescription, suitePrice;
        Button btnReservar;

        public SuiteViewHolder(@NonNull View itemView) {
            super(itemView);
            suiteImage = itemView.findViewById(R.id.suite_image);
            suiteName = itemView.findViewById(R.id.suite_name);
            suiteDescription = itemView.findViewById(R.id.suite_description);
            suitePrice = itemView.findViewById(R.id.suite_price);
            btnReservar = itemView.findViewById(R.id.btnReservarSuite);
        }
    }
}