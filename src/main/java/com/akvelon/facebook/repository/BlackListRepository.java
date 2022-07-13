package com.akvelon.facebook.repository;

public interface BlackListRepository {
    public void save(String token);

    public boolean exists(String token);
}
