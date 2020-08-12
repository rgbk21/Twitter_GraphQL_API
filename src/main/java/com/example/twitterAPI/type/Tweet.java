package com.example.twitterAPI.type;

import com.google.common.base.Objects;

public class Tweet {

    private String id;
    private String author_id;
    private String created_at;
    private String in_reply_to_user_id;
    private String text;


    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }

    public void setIn_reply_to_user_id(String in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return Objects.equal(author_id, tweet.author_id) &&
                Objects.equal(created_at, tweet.created_at) &&
                Objects.equal(id, tweet.id) &&
                Objects.equal(in_reply_to_user_id, tweet.in_reply_to_user_id) &&
                Objects.equal(text, tweet.text);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(author_id, created_at, id, in_reply_to_user_id, text);
    }
}
