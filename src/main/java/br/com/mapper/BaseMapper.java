package br.com.mapper;

public interface BaseMapper<REQ, RES, ENT> {
    RES toResponse(ENT entity);
    ENT toEntity(REQ request);
}

