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
//    private BookingAdapter.RecyclerViewClickListener logsListener;

//    public BookingAdapter(Context c) {
//        this.context = c;
//    }

    public void setBookingList(List<Booking> bookings){
        this.bookingList = bookings;
//        this.logsListener = listener;
        notifyDataSetChanged();
    }

//    public interface RecyclerViewClickListener {
//        void OnClick(View v, int position);
//    }
//
//    public void RecyclerViewClickListener (BookingAdapter.RecyclerViewClickListener listener){
//        this.logsListener = listener;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView locations , date , status;
        Button view;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.locations_tv);
            date = itemView.findViewById(R.id.date_tv);
            status = itemView.findViewById(R.id.status_tv);
            view = itemView.findViewById(R.id.button);
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
        String destination,start, locations;
        destination = bookingList.get(position).getSchedule().getDestination().getName();
        start = bookingList.get(position).getSchedule().getStartingPoint().getName();
        locations = context.getString(R.string.locations,start,destination);

        holder.locations.setText(locations);
        holder.date.setText(bookingList.get(position).getSchedule().getScheduleDate());
        holder.status.setText(bookingList.get(position).getStatus());

        holder.view.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(v.getRootView().getContext());
            View dialogView = LayoutInflater.from(v.getRootView().getContext())
                    .inflate(R.layout.receipt_dialog,null);

            TextView transId, plateNo, locations1, fare , quantity, gTotal;
            Button bookingPay, bookingCancel;
            ImageView close;

            transId = dialogView.findViewById(R.id.transId_tv);
            plateNo = dialogView.findViewById(R.id.plate_tv);
            locations1 = dialogView.findViewById(R.id.location_tv);
            fare = dialogView.findViewById(R.id.fare_tv);
            quantity = dialogView.findViewById(R.id.qty_tv);
            gTotal = dialogView.findViewById(R.id.gTotal_tv);

            close = dialogView.findViewById(R.id.close_btn);
            bookingPay = dialogView.findViewById(R.id.payment_btn);
            bookingCancel = dialogView.findViewById(R.id.cancel_btn);

            //SET DATA
            String a,b,c;
            b = bookingList.get(position).getSchedule().getDestination().getName();
            a = bookingList.get(position).getSchedule().getStartingPoint().getName();
            c = context.getString(R.string.locations,a,b);
            locations1.setText(c);
            transId.setText(Integer.toString(bookingList.get(position).getId()));
            plateNo.setText(bookingList.get(position).getBus().getPlateNumber());
            fare.setText(Integer.toString(bookingList.get(position).getFareAmount()));
            quantity.setText(Integer.toString(bookingList.get(position).getQuantity()));
            gTotal.setText(Integer.toString(bookingList.get(position).getGrandTotal()));
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            bookingPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("CLICK", "YOU CLICKED BOOKING PAY BUTTON");
                    Intent intent = new Intent(context, PaymentActivity.class);
                    context.startActivity(intent);
                }
            });
            bookingCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
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
