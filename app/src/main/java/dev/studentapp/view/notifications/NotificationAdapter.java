package dev.studentapp.view.notifications;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import dev.studentapp.R;
import dev.studentapp.model.dataset.notifications.NotificationItemDataset;
import dev.studentapp.model.dataset.notifications.NotificationsDataset;
import dev.studentapp.model.dataset.notifications.PagingDataset;
import dev.studentapp.util.CommonMethods;

/**
 * Created by Nirav-Dangi on 01/09/16.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>  {

    private ArrayList<NotificationItemDataset> notificationItemDatasetArrayList;
    private PagingDataset pagingDataset;

    private NotificationListActivity notificationListActivity;

    public NotificationAdapter(NotificationListActivity notificationListActivity, NotificationsDataset notificationsDataset) {
        this.notificationListActivity = notificationListActivity;
        this.notificationItemDatasetArrayList = notificationsDataset.getNotificationItemDatasetArrayList();
        this.pagingDataset = notificationsDataset.getPagingDataset();
    }

    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        CardView view = (CardView) inflater.inflate(R.layout.notification_item, viewGroup, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final NotificationItemDataset notificationItemDataset = notificationItemDatasetArrayList.get(position);

        holder.notificationText.setText(notificationItemDataset.getNotification());
        holder.timestamp.setText(CommonMethods.getCreatedText(notificationItemDataset.getCreated()));

        CommonMethods.loadImage(notificationListActivity,holder.imgView,notificationItemDataset.getAvatar());

        holder.lytMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificationListActivity.redirectToFeedDetail(notificationItemDataset.getPostId());
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);

    }

    @Override
    public int getItemCount() {
        return notificationItemDatasetArrayList.size();
    }

    public void newNotificationItemsAdded(NotificationsDataset notificationsDataset) {

        pagingDataset = notificationsDataset.getPagingDataset();
        notificationItemDatasetArrayList.addAll(notificationsDataset.getNotificationItemDatasetArrayList());

        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public View mItemView;
        public ImageView imgView;
        public TextView notificationText;
        public TextView timestamp;

        public LinearLayout lytMain;

        public ViewHolder(View itemView) {
            super(itemView);

            mItemView = itemView;

            imgView = (ImageView) itemView.findViewById(R.id.imgView);
            notificationText = (TextView) itemView.findViewById(R.id.notificationText);
            timestamp = (TextView) itemView.findViewById(R.id.timestamp);

            lytMain = (LinearLayout) itemView.findViewById(R.id.lytMain);
        }
    }

    public long getTotalPageCount() {
        return pagingDataset.getTotalPageCount();
    }

    public long getCurrentPageIndex() {
        return pagingDataset.getCurrentPageIndex();
    }
}
