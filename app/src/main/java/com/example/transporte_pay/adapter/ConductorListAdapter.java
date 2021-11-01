package com.example.transporte_pay.adapter;

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
import com.example.transporte_pay.views.activity.MapsActivity;
import com.example.transporte_pay.views.activity.PaymentActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConductorListAdapter extends RecyclerView.Adapter<ConductorListAdapter.ViewHolder> {
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

        Button view,geo;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            locations = itemView.findViewById(R.id.locations_tv);
            date = itemView.findViewById(R.id.date_tv);
            status = itemView.findViewById(R.id.status_tv);
            view = itemView.findViewById(R.id.button);
            geo = itemView.findViewById(R.id.geobutton);
            name = itemView.findViewById(R.id.passengerName_tv);
        }
    }
    @NonNull
    @Override
    public ConductorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new ConductorListAdapter.ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.booking_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ConductorListAdapter.ViewHolder holder, int position) {
        String destination, start, locations, name,bus_gcash_number,longitude,latitude,platenumber;
        int status,bus_id;

        if (roleId == 2) {
            holder.name.setVisibility(View.VISIBLE);
        }

        name = bookingList.get(position).getUser().getName();
        status = bookingList.get(position).getStatus().getId();
        bus_gcash_number = bookingList.get(position).getBus().getGcashNumber();
        bus_id = bookingList.get(position).getBus().getId();
        latitude = bookingList.get(position).getBus().getLatitude();
        longitude = bookingList.get(position).getBus().getLongitude();
        platenumber = bookingList.get(position).getBus().getPlateNumber();
        holder.name.setText(name);
        destination = bookingList.get(position).getSchedule().getDestination().getName();
        start = bookingList.get(position).getSchedule().getStartingPoint().getName();
        locations = context.getString(R.string.locations, start, destination);
        if (status == 6) {
            holder.geo.setVisibility(View.VISIBLE);
        }
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
            }else if (status !=1){
                bookingPay.setVisibility(View.GONE);
                bookingCancel.setVisibility(View.GONE);
            }

            bookingPay.setOnClickListener(v13 -> {
                Log.e("CLICK", "YOU CLICKED BOOKING PAY BUTTON");
                Intent intent = new Intent(context, PaymentActivity.class);
                intent.putExtra("busGcash", bus_gcash_number);
                intent.putExtra("fullname", name);
                intent.putExtra("bus_id", Integer.toString(bus_id));
                intent.putExtra("transId", Integer.toString(bookingList.get(position).getId()));
                context.startActivity(intent);
            });
            bookingCancel.setOnClickListener(v12 -> {

            });
            builder.setView(dialogView)
                    .setCancelable(true)
                    .show();
        });

        holder.geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);
                intent.putExtra("fullname", name);
                intent.putExtra("platenumber", platenumber);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
