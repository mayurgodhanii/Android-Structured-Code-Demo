package dev.studentapp.model.dataset.comments;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Nirav Dangi on 12/08/16.
 */
public class CommentItemDataset implements Parcelable{

    private long id;
    private long postId;

    private User user;

    private String text;

    private long likeCount;
    private long unLikeCount;
    private boolean commentLikeByUser;
    private boolean commentDisLikeByUser;

    private long created;

    public CommentItemDataset(JSONObject jsonObject) throws JSONException {

        id = jsonObject.getLong("id");
        postId = jsonObject.getLong("post_id");
        text = jsonObject.getString("text");

        user = new User(jsonObject.getJSONObject("user"));

        likeCount = jsonObject.getLong("like_count");
        unLikeCount = jsonObject.getLong("unlike_count");
        commentLikeByUser = jsonObject.getInt("comment_like_by_user")==0?false:true;
        commentDisLikeByUser = jsonObject.getInt("comment_dislike_by_user")==0?false:true;

        created = jsonObject.getLong("created");
    }

    protected CommentItemDataset(Parcel in) {
        id = in.readLong();
        postId = in.readLong();
        user = in.readParcelable(User.class.getClassLoader());
        text = in.readString();
        likeCount = in.readLong();
        unLikeCount = in.readLong();
        commentLikeByUser = in.readByte() != 0;
        commentDisLikeByUser = in.readByte() != 0;
        created = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(postId);
        dest.writeParcelable(user, flags);
        dest.writeString(text);
        dest.writeLong(likeCount);
        dest.writeLong(unLikeCount);
        dest.writeByte((byte) (commentLikeByUser ? 1 : 0));
        dest.writeByte((byte) (commentDisLikeByUser ? 1 : 0));
        dest.writeLong(created);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommentItemDataset> CREATOR = new Creator<CommentItemDataset>() {
        @Override
        public CommentItemDataset createFromParcel(Parcel in) {
            return new CommentItemDataset(in);
        }

        @Override
        public CommentItemDataset[] newArray(int size) {
            return new CommentItemDataset[size];
        }
    };

    public long getId() {
        return id;
    }

    public long getPostId() {
        return postId;
    }

    public User getUser() {
        return user;
    }

    public String getText() {
        return text;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public long getUnLikeCount() {
        return unLikeCount;
    }

    public boolean isCommentLikeByUser() {
        return commentLikeByUser;
    }

    public boolean isCommentDisLikeByUser() {
        return commentDisLikeByUser;
    }

    public long getCreated() {
        return created;
    }
}
