package dev.studentapp.model.dataset.feedlisting;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nirav Dangi on 29/06/16.
 */
public class FeedItemDataset implements Parcelable{

    private long id;
    private String postType;
    private String text;
    private String jsonText;

    private HTMLText htmlText;

    private long likeCount;
    private long unlikeCount;
    private long postLikeByUser;
    private long postDislikeByUser;
    private long commentCount;
    private long bookmarkCount;
    private long inappropriateCount;
    private long score;
    private long sortOrder;
    private long reads;
    private long spamCount;

    private ArrayList<PostUploads> postUploadsArrayList;

    private Category category;

    private PostBy postBy;

    private String tags;

    private long created;

    private long lastActivityDate;

    /*
        {
            "id": 20,
            "post_type": "text",
            "text": "Hello World Test Data",
            "json_text": "",
            "html_text": {
              "short_desc": "",
              "title": "",
              "link": "",
              "asset": ""
            },
            "like_count": 0,
            "unlike_count": 0,
            "post_like_by_user": 0,
            "post_dislike_by_user": 0,
            "comment_count": 0,
            "bookmark_count": 0,
            "inappropriate_count": 0,
            "score": 0,
            "sort_order": 0,
            "reads": 0,
            "spam_count": 0,
            "post_uploads": [],
            "category": {
              "id": 1,
              "taxonomy_id": 1,
              "name": "Campus"
            },
            "postby": {
              "id": 2,
              "avatar": "",
              "full_name": "Test User",
              "profile_type": 2,
              "current_position": "",
              "alias": "",
              "summary": "Test"
            },
            "tags": "test",
            "created": 1468333200,
            "last_activity_date": 1468333200
        }
     */

    public FeedItemDataset(JSONObject jsonObject) throws JSONException {

        id = jsonObject.getLong("id");

        postType = jsonObject.getString("post_type");
        text = jsonObject.getString("text");
        jsonText = jsonObject.getString("json_text");

        htmlText = new HTMLText(jsonObject.getJSONObject("html_text"));

        likeCount = jsonObject.getLong("like_count");
        unlikeCount = jsonObject.getLong("unlike_count");
        postLikeByUser = jsonObject.getLong("post_like_by_user");
        postDislikeByUser = jsonObject.getLong("post_dislike_by_user");
        commentCount = jsonObject.getLong("comment_count");
        bookmarkCount = jsonObject.getLong("bookmark_count");
        inappropriateCount = jsonObject.getLong("inappropriate_count");
        score = jsonObject.getLong("score");
        sortOrder = jsonObject.getLong("sort_order");
        reads = jsonObject.getLong("reads");
        spamCount = jsonObject.getLong("spam_count");

        JSONArray postUploadsJsonArray = jsonObject.getJSONArray("post_uploads");
        postUploadsArrayList = new ArrayList<PostUploads>();
        for (int i = 0; i < postUploadsJsonArray.length(); i++) {
            postUploadsArrayList.add(new PostUploads(postUploadsJsonArray.getJSONObject(i)));
        }

        category = new Category(jsonObject.getJSONObject("category"));

        postBy = new PostBy(jsonObject.getJSONObject("postby"));

        tags = jsonObject.getString("tags");

        created = jsonObject.getLong("created");
        lastActivityDate = jsonObject.getLong("last_activity_date");
    }

    protected FeedItemDataset(Parcel in) {
        id = in.readLong();
        postType = in.readString();
        text = in.readString();
        jsonText = in.readString();
        htmlText = in.readParcelable(HTMLText.class.getClassLoader());
        likeCount = in.readLong();
        unlikeCount = in.readLong();
        postLikeByUser = in.readLong();
        postDislikeByUser = in.readLong();
        commentCount = in.readLong();
        bookmarkCount = in.readLong();
        inappropriateCount = in.readLong();
        score = in.readLong();
        sortOrder = in.readLong();
        reads = in.readLong();
        spamCount = in.readLong();
        postUploadsArrayList = in.createTypedArrayList(PostUploads.CREATOR);
        category = in.readParcelable(Category.class.getClassLoader());
        postBy = in.readParcelable(PostBy.class.getClassLoader());
        tags = in.readString();
        created = in.readLong();
        lastActivityDate = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(postType);
        dest.writeString(text);
        dest.writeString(jsonText);
        dest.writeParcelable(htmlText, flags);
        dest.writeLong(likeCount);
        dest.writeLong(unlikeCount);
        dest.writeLong(postLikeByUser);
        dest.writeLong(postDislikeByUser);
        dest.writeLong(commentCount);
        dest.writeLong(bookmarkCount);
        dest.writeLong(inappropriateCount);
        dest.writeLong(score);
        dest.writeLong(sortOrder);
        dest.writeLong(reads);
        dest.writeLong(spamCount);
        dest.writeTypedList(postUploadsArrayList);
        dest.writeParcelable(category, flags);
        dest.writeParcelable(postBy, flags);
        dest.writeString(tags);
        dest.writeLong(created);
        dest.writeLong(lastActivityDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FeedItemDataset> CREATOR = new Creator<FeedItemDataset>() {
        @Override
        public FeedItemDataset createFromParcel(Parcel in) {
            return new FeedItemDataset(in);
        }

        @Override
        public FeedItemDataset[] newArray(int size) {
            return new FeedItemDataset[size];
        }
    };

    public long getId() {
        return id;
    }

    public String getPostType() {
        return postType;
    }

    public String getText() {
        return text;
    }

    public String getJsonText() {
        return jsonText;
    }

    public HTMLText getHtmlText() {
        return htmlText;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public long getUnlikeCount() {
        return unlikeCount;
    }

    public long getPostLikeByUser() {
        return postLikeByUser;
    }

    public long getPostDislikeByUser() {
        return postDislikeByUser;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public long getBookmarkCount() {
        return bookmarkCount;
    }

    public long getInappropriateCount() {
        return inappropriateCount;
    }

    public long getScore() {
        return score;
    }

    public long getSortOrder() {
        return sortOrder;
    }

    public long getReads() {
        return reads;
    }

    public long getSpamCount() {
        return spamCount;
    }

    public ArrayList<PostUploads> getPostUploadsArrayList() {
        return postUploadsArrayList;
    }

    public Category getCategory() {
        return category;
    }

    public PostBy getPostBy() {
        return postBy;
    }

    public String getTags() {
        return tags;
    }

    public long getCreated() {
        return created;
    }

    public long getLastActivityDate() {
        return lastActivityDate;
    }
}
