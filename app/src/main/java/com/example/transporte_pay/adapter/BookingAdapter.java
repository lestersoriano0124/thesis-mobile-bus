package com.example.transporte_pay.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transporte_pay.R;
import com.example.transporte_pay.data.model.Booking;
import com.example.transporte_pay.views.activity.PaymentActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private List<Booking> bookingList;
    private Context context;
    private Integer roleId;

    public void setBookingList(List<Booking> bookings, Integer r) {
        this.bookingList = bookings;
        this.roleId = r;
//        this.logsListener = listener;
        notifyDataSetChanged();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView locations, date, status, name;
        Button view;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.locations_tv);
            date = itemView.findViewById(R.id.date_tv);
            status = itemView.findViewById(R.id.status_tv);
            view = itemView.findViewById(R.id.button);
            name = itemView.findViewById(R.id.passengerName_tv);
        }
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
        String destination, start, locations, name;

        if (roleId == 2) {
            holder.name.setVisibility(View.VISIBLE);
        }

        name = bookingList.get(position).getUser().getName();
        holder.name.setText(name);
        destination = bookingList.get(position).getSchedule().getDestination().getName();
        start = bookingList.get(position).getSchedule().getStartingPoint().getName();
        locations = context.getString(R.string.locations, start, destination);

        holder.locations.setText(locations);
        holder.date.setText(bookingList.get(position).getSchedule().getScheduleDate());
        holder.status.setText(bookingList.get(position).getStatus().getTitle());

        holder.view.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
            View dialogView = LayoutInflater.from(v.getRootView().getContext())
                    .inflate(R.layout.receipt_dialog, null);

            TextView transId, plateNo, locations1, fare, quantity, gTotal;
            Button bookingPay, bookingCancel;
            ImageView close;

            transId = dialogView.findViewById(R.id.transId_tv);
            plateNo = dialogView.findViewById(R.id.plate_tv);
            locations1 = dialogView.findViewById(R.id.location_tv);
            fare = dialogView.findViewById(R.id.fare_tv);
            quantity = dialogView.findViewById(R.id.qty_tv);
            gTotal = dialogView.findViewById(R.id.gTotal_tv);

            bookingPay = dialogView.findViewById(R.id.payment_btn);
            bookingCancel = dialogView.findViewById(R.id.cancel_btn);

            //SET DATA
            String a, b, c;
            b = bookingList.get(position).getSchedule().getDestination().getName();
            a = bookingList.get(position).getSchedule().getStartingPoint().getName();
            c = context.getString(R.string.locations, a, b);
            locations1.setText(c);
            transId.setText(Integer.toString(bookingList.get(position).getId()));
            plateNo.setText(bookingList.get(position).getBus().getPlateNumber());
            fare.setText(Integer.toString(bookingList.get(position).getFareAmount()));
            quantity.setText(Integer.toString(bookingList.get(position).getQuantity()));
            gTotal.setText(Integer.toString(bookingList.get(position).getGrandTotal()));

            if (roleId == 2) {
                bookingCancel.setVisibility(View.GONE);
                bookingPay.setVisibility(View.GONE);
            }

            bookingPay.setOnClickListener(v13 -> {
                Log.e("CLICK", "YOU CLICKED BOOKING PAY BUTTON");
                Intent intent = new Intent(context, PaymentActivity.class);
                context.startActivity(intent);
            });
            bookingCancel.setOnClickListener(v12 -> {

            });
            builder.setView(dialogView)
                    .setCancelable(true)
                    .show();
        });

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }
}
