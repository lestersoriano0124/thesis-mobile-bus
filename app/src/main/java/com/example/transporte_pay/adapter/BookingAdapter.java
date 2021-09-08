package com.example.transporte_pay.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.model.Booking;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private List<Booking> bookingList;
    private Context context;

    public BookingAdapter(Context c) {
        this.context = c;
    }

    public void setBookingList(List<Booking> bookings){
        this.bookingList = bookings;
        notifyDataSetChanged();
    }


    @NonNull
    @NotNull
    @Override
    public BookingAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.booking_list, parent, false));
    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String destination,start, locations;
        destination = bookingList.get(position).getSchedule().getDestination().getName();
        start = bookingList.get(position).getSchedule().getStartingPoint().getName();
        locations = context.getString(R.string.locations,start,destination);

        holder.locations.setText(locations.toString());
        holder.date.setText(bookingList.get(position).getSchedule().getScheduleDate());
        holder.status.setText(bookingList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView locations , date , status;
//                locations_tv, date_tv, status_tv
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.locations_tv);
            date = itemView.findViewById(R.id.date_tv);
            status = itemView.findViewById(R.id.status_tv);
        }
    }
}
