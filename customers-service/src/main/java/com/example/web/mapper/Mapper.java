package com.example.web.mapper;

public interface Mapper<R, E> {
    E map(E response, R request);
}