package com.ndmsystems.knext.ui.devices.list;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ndmsystems.knext.R;
import com.ndmsystems.knext.helpers.ReachabilityHelper;
import com.ndmsystems.knext.models.userAccount.device.DeviceModel;

import java.util.List;

public class NetworkDevicesListAdapter extends ArrayAdapter<DeviceModel> {
    private final LayoutInflater mLayoutInflater;
    private final Activity context;
    private final List<DeviceModel> devices;

    NetworkDevicesListAdapter(List<DeviceModel> allDevices, Activity context) {
        super(context, R.layout.device_element, allDevices);
        this.devices = allDevices;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public DeviceModel getItem(int pos) {
        return devices.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @NonNull
    @Override
    public View getView(final int pos, View convertView, @NonNull ViewGroup viewGroup) {
        final DeviceModel deviceModel = devices.get(pos);

        ViewHolder holder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.device_element, viewGroup, false);
            holder = new ViewHolder();
            holder.ivDeviceImage = convertView.findViewById(R.id.ivDeviceImage);
            holder.tvDeviceName = convertView.findViewById(R.id.tvDeviceName);
            holder.tvDeviceModel = convertView.findViewById(R.id.tvDeviceModel);
            holder.ivIsCurrentDevice = convertView.findViewById(R.id.ivIsCurrentDevice);
            holder.tvDeviceStatus = convertView.findViewById(R.id.tvDeviceStatus);
            holder.deviceStatusLoading = convertView.findViewById(R.id.deviceStatusLoading);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();

        holder.tvDeviceName.setText(deviceModel.getName());
        holder.tvDeviceModel.setText(String.format(
                "%s, %s",
                deviceModel.getModel(),
                deviceModel.getType().toVisibleString()
        ));

        holder.ivIsCurrentDevice.setVisibility(View.GONE);

        if (deviceModel.isOnlineStatusDefined()) {
            holder.deviceStatusLoading.setVisibility(View.INVISIBLE);
            holder.ivDeviceImage.setVisibility(View.VISIBLE);
            if (deviceModel.isOnline()) {
                holder.ivDeviceImage.setImageResource(R.drawable.ic_device_green);
                holder.tvDeviceStatus.setText(ReachabilityHelper.getLongName(deviceModel.getLastAvailableReachability()));
                holder.tvDeviceStatus.setTextColor(context.getResources().getColor(R.color.zy_light_moss_green));
            } else {
                holder.ivDeviceImage.setImageResource(R.drawable.ic_device_offline);
                holder.tvDeviceStatus.setText(R.string.device_offline);
                holder.tvDeviceStatus.setTextColor(context.getResources().getColor(R.color.zy_cool_gray));
            }
        }else{
            holder.deviceStatusLoading.setVisibility(View.VISIBLE);
            holder.ivDeviceImage.setVisibility(View.INVISIBLE);
            holder.tvDeviceStatus.setText(R.string.device_offline);
            holder.tvDeviceStatus.setTextColor(context.getResources().getColor(R.color.zy_cool_gray));
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView ivDeviceImage;
        TextView tvDeviceName;
        TextView tvDeviceModel;
        ImageView ivIsCurrentDevice;
        TextView tvDeviceStatus;
        View deviceStatusLoading;
    }
}
