package com.sms.service;

import java.util.List;

public interface Mappable<E, T> {

	public T convertToModel(E domainObject);

	public E convertToDomain(T modelObject);

	public List<T> convertToModelList(List<E> domainlist);

	public List<E> convertToDomainList(List<T> modelList);

}
